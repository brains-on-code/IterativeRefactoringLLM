import os
import textwrap
from copy import deepcopy

import networkx as nx
from networkx import NetworkXError
from networkx.drawing.nx_agraph import to_agraph

import config.fields
import config.utils

# Setup logging
logging, handler = config.utils.setup_logger("CodeDiffGraph")


class CodeDiffGraph:

    def __init__(self):

        self.code_graph = nx.MultiDiGraph()  # Directed graph with nodes=code lines and edges= transition | ratio | change_type

        # TODO: transitions graph brauchen wir vermutlich gar nicht später, weil wir die Daten im Graph Report CSV erfassen und anders visualisieren als in nem transitions Graph
        self.transitions_graph = nx.MultiDiGraph()  # Directed Graph with nodes=versions and edges= change_type | counter

    # ------------------------------------------------------------------------------------
    #             Main Graph Utility Methods
    # ------------------------------------------------------------------------------------

    @staticmethod
    def _format_version(version: int) -> str:
        if isinstance(version, int):
            return f"v{version}"
        raise ValueError(f"Invalid version format: {version}")

    @staticmethod
    def _validate_edge_input(old_line, new_line, version_from, version_to, ratio, change_type):
        if not isinstance(old_line, str) or not old_line.strip():
            raise ValueError("old_line must be a non-empty string.")
        if not isinstance(new_line, str) or not new_line.strip():
            raise ValueError("new_line must be a non-empty string.")
        if not isinstance(version_from, int):
            raise TypeError("version_from must be an integer.")
        if not isinstance(version_to, int):
            raise TypeError("version_to must be an integer.")
        if version_to - version_from != 1:
            raise ValueError("we only consider direct successors in graphs currently!")
        if not isinstance(ratio, (float, int)):
            raise TypeError("ratio must be a float.")
        # if not isinstance(change_type, str): # TODO: bauen wir gerade um.
        #     raise TypeError("change_type must be a string.")

    # ------------------------------------------------------------------------------------

    def add_edges(self, old_line: str, new_line: str, version_from: int, version_to: int, ratio=-1.0,
                  change_type="tba"):

        """
        Adds a code change as a directed edge to the code graph and updates the transition graph.

        This method validates input and ensures that all nodes and edges are consistent with the expected structure.
        Duplicate edges with identical attributes are not added.


        Args:
            old_line (str): The original line of code before the change.
            new_line (str): The new line of code after the change.
            version_from (int): The source version of the change (e.g. 1).
            version_to (int): The target version of the change (e.g. 2).
            ratio (float, optional): A similarity score between the lines, default is -1.0.
            change_type (str, optional): A label describing the type of change, default is "tba".

        Raises:
            ValueError: If `old_line` or `new_line` are not valid non-empty strings.
            TypeError: If `version_from`, `version_to`, `ratio`, or `change_type` are not of the expected types.

        Side Effects:
            Updates `self.code_graph` by adding a directed edge from `old_line` to `new_line`.
            Updates `self.transitions_graph` to increment the version transition count for the specified change type.
        """

        self._validate_edge_input(old_line, new_line, version_from, version_to, ratio, change_type)
        version_from = self._format_version(version_from)
        version_to = self._format_version(version_to)

        logging.info(f"add change: {old_line} → {new_line}\n{version_from} → {version_to}")
        self.code_graph.add_edge(
            old_line, new_line,
            version_from=version_from,
            version_to=version_to,
            key=f"{version_from} → {version_to}",
            ratio=ratio,
            change_type=change_type
        )

        self.update_transition_graph(version_from, version_to, change_type)

    # ------------------------------------------------------------------------------------

    def update_transition_graph(self, u,  v, change_type):
        """
        Updates the transitions_graph by incrementing the count of a change between versions.

        If an edge from node `u` to node `v` with the specified `change_type` already exists,
        its 'count' attribute is incremented by 1. If no such edge exists, a new edge is added
        with the given `change_type` and an initial count of 1.

        Parameters:
            u (str): The source version node (e.g., 'v1').
            v (str): The target version node (e.g., 'v2').
            change_type (str): The type of change between the two versions (e.g., 'modification', 'insertion').

        Side Effects:
            Modifies the internal `transitions_graph` by adding or updating edges.
        """


        edges = self.transitions_graph.get_edge_data(u, v, default={})

        for data in edges.values():
            if data.get(config.fields.change_type) == change_type:
                data['count'] += 1
                return

        # If no edge was found with this change_type
        self.transitions_graph.add_edge(u, v, change_type=change_type, count=1)

    # ------------------------------------------------------------------------------------

    def merge_with(self, other: 'CodeDiffGraph') -> 'CodeDiffGraph':
        """
        Creates and returns a new CodeDiffGraph object that combines the contents of `self` and `other`.

        This method performs a non-destructive merge: neither `self` nor `other` is modified. Both the
        `code_graph` and `transitions_graph` are combined using `networkx.compose()` and the result is
        returned as a new `CodeDiffGraph` instance.

        Args:
            other (CodeDiffGraph): Another CodeDiffGraph instance to merge with.

        Returns:
            CodeDiffGraph: A new graph object containing the merged code and transition graphs.

        Raises:
            TypeError: If `other` is not an instance of CodeDiffGraph, or if its internal graphs are not MultiDiGraphs.
        """

        if not isinstance(other, CodeDiffGraph):
            raise TypeError("Expected CodeDiffGraph instance for merging.")

        # Ensure that both internal graphs are MultiDiGraphs
        if not isinstance(other.code_graph, nx.MultiDiGraph) or not isinstance(other.transitions_graph,
                                                                               nx.MultiDiGraph):
            raise TypeError("Both graphs in 'other' must be MultiDiGraph instances.")

        merged = deepcopy(self)

        # Create combined graphs
        merged.code_graph = nx.compose(self.code_graph, other.code_graph)

        # For the transition graph, we need to merge the attributes more carefully (nx.compose doesn't do that per se)
        for u, v, data in other.transitions_graph.edges(data=True):
            change_type = data.get(config.fields.change_type)
            count = data.get("count", 1)

            edges = merged.transitions_graph.get_edge_data(u, v, default={})
            found = False
            for d in edges.values():
                if d.get(config.fields.change_type) == change_type:
                    d["count"] += count
                    found = True
                    break

            if not found:
                merged.transitions_graph.add_edge(u, v, **data)

        return merged

    # ------------------------------------------------------------------------------------

    def save_transition_graph(self, filepath):

        """
        Saves the transition graph to disk as GraphML files and PNG images
        for visualization.

        Args:
            filepath (str): The base file path (without extension) where the graphs should be saved.

        Side Effects:
            Creates the following files:
            - {filepath}_transitions.{graphml, png}

        Logs info messages upon successful saving of each file.
        """

        output_file_graphml = filepath + "_transitions.graphml"
        output_file_png = filepath + "_transitions.png"


        if os.path.exists(output_file_graphml):
            logging.debug(f"{output_file_graphml} already exists (skip saving again ...)")

        else:
            # Save graph as .graphml-file
            nx.write_graphml(self.transitions_graph, output_file_graphml)
            logging.debug(f"Transition Graph saved as {output_file_graphml}.")


        if os.path.exists(output_file_png):
            logging.debug(f"{output_file_png} already exists (skip saving again ...)")

        else:
            # Save graph as png
            G_transition_copy = self._prepare_transition_graph_for_visualization()
            self._graph_to_png(G_transition_copy, output_file_png)
            logging.debug(f"Transition Graph saved as {output_file_png}.")


    def save_code_graph(self, filepath):

        """
       Saves the code graph to disk as GraphML files and PNG images
       for visualization.

       Args:
           filepath (str): The base file path (without extension) where the graphs should be saved.

       Side Effects:
           Creates the following files:
           - {filepath}_code.{graphml, png}

       Logs info messages upon successful saving of each file.
       """

        output_file_graphml = filepath + "_code.graphml"
        output_file_png = filepath + "_code.png"

        # Save graph as .graphml-file
        if os.path.exists(output_file_graphml):
            logging.debug(f"[SKIP] {output_file_graphml} already exists.")

        else:
            nx.write_graphml(self.code_graph, output_file_graphml)
            logging.debug(f"Code Graph saved as {output_file_graphml}.")


        # Save graph as png
        if os.path.exists(output_file_png):
            logging.debug(f"[SKIP] {output_file_png} already exists.")

        else:
            G_code_copy = self._prepare_code_graph_for_visualization()
            self._graph_to_png(G_code_copy, output_file_png)
            logging.debug(f"Code Graph saved as {output_file_png}.")


    # ------------------------------------------------------------------------------------
    #             SOME USEFUL GETTERS & SETTER(S)
    # ------------------------------------------------------------------------------------

    def get_original(self, modified_line):
        """Finds the original line of a given version."""
        try:
            predecessors = list(self.code_graph.predecessors(modified_line))
            return predecessors[0] if predecessors else modified_line
        except NetworkXError as n:
            raise KeyError(f"Node {n} is not in the graph!")

    def get_change_count(self, original_line):
        """Counts the number of changes for a line."""
        return len(self.get_versions(original_line))

    def get_versions(self, line):
        """Returns all versions of an original line."""

        """ Note: nx.descendants returns all nodes that are reachable from node, i.e. 
        there is at least one path from node to descendant, not necessarily a direct edge!"""

        return list(nx.descendants(self.code_graph, line))

    def get_all_original_nodes(self, dummy_root=config.fields.DUMMY_ROOT):
        """Finds all lines from version v0 (=original lines)."""
        result = set()
        for node in self.code_graph.nodes:
            preds = list(self.code_graph.predecessors(node))
            if dummy_root in preds:
                result.add(node)
        return result

    def get_leaf_dummy_children(self, dummy_root=config.fields.DUMMY_ROOT) -> set:
        """
        Returns all nodes that are connected only from the dummy root and have no successors.

        These nodes represent unchanged lines across all versions.

        Args:
            dummy_root (str): The dummy root node identifier. Defaults to the value from config.DUMMY_ROOT.

        Returns:
            set: A set of nodes that have the dummy root as their only predecessor and no outgoing edges.
        """
        result = set()
        for node in self.code_graph.nodes:
            preds = list(self.code_graph.predecessors(node))
            if preds == [dummy_root] and self.code_graph.out_degree(node) == 0:
                result.add(node)
        return result


    # ------------------------------------------------------------------------------------
    #             HELPER FUNCTIONS FOR VISUALIZING
    # ------------------------------------------------------------------------------------

    @staticmethod
    def _shorten_long_labels(text, width=30):
        """Helper function to shorten long node labels."""
        return textwrap.shorten(text, width=width, placeholder="...")

    @staticmethod
    def _graph_to_png(graph: nx.MultiDiGraph, filepath: str) -> None:
        """Converts a NetworkX MultiDiGraph to a PNG image file using Graphviz."""

        try:
            A = to_agraph(graph)
            A.layout("dot")
            A.draw(filepath)
            logging.debug(f"Graph saved as {filepath}")

        except Exception as e:
            logging.error(f"[ERROR] Could not save graph ({filepath}) due to {type(e).__name__}.")

    def _remove_leaf_dummy_children(self, dummy_root=config.fields.DUMMY_ROOT) -> nx.MultiDiGraph:
        """
        Removes nodes that are only connected from the dummy root
        and have no descendants themselves.

        USE ONLY FOR COMPACT VISUALIZATION!
        """
        G_filtered = self.code_graph.copy()
        for node in list(self.code_graph.nodes):
            preds = list(self.code_graph.predecessors(node))
            if preds == [dummy_root] and self.code_graph.out_degree(node) == 0:
                G_filtered.remove_node(node)
        return G_filtered

    def _prepare_code_graph_for_visualization(self, shorten_labels=True) -> nx.MultiDiGraph:
        """
        Returns a filtered and labeled copy of the code graph prepared for visualization.

        This method removes leaf dummy children nodes (unchanged lines) from the graph
        to declutter the visualization. It sets node labels to either shortened or full
        string representations depending on the `shorten_labels` flag.

        Edge labels are formatted to include the edge key, the weight (ratio) with two decimals,
        and the change type, in the format:
            "{key} (w={ratio:.2f}) | {change_type}"

        Args:
            shorten_labels (bool): If True, node labels are shortened using the internal
                                   `_shorten_long_labels` method. If False, full node names
                                   are used. Defaults to True.

        Returns:
            nx.MultiDiGraph: A copy of the code graph filtered and labeled for visualization.
        """
        G = self._remove_leaf_dummy_children()

        if shorten_labels:
            short_labels = {node: self._shorten_long_labels(node) for node in G.nodes}
            for node in G.nodes:
                G.nodes[node]["label"] = short_labels.get(node, str(node))
        else:
            for node in G.nodes:
                G.nodes[node]["label"] = str(node)

        for u, v, key, d in G.edges(data=True, keys=True):
            ratio = d.get(config.fields.ratio, "?")
            change_type = d.get(config.fields.change_type, "?")
            label = f"{key} (w={ratio:.2f}) | {change_type}"
            G.edges[u, v, key]["label"] = label

        return G

    def _prepare_transition_graph_for_visualization(self) -> nx.MultiDiGraph:
        """
        Prepares a copy of the transition graph with labeled edges for visualization.

        The edge labels are composed of the 'change_type' and 'count' attributes,
        formatted as "{change_type} | count={count}". If these attributes are missing,
        a placeholder "?" is used.

        Returns:
            nx.MultiDiGraph: A copy of the transition graph with updated edge labels
                             suitable for visualization.
        """
        G = self.transitions_graph.copy()

        for u, v, key, d in G.edges(data=True, keys=True):
            change_type = d.get(config.fields.change_type, "?")
            count = d.get("count", "?")
            G.edges[u, v, key]["label"] = f"{change_type} (x{count})"

        return G

    # ------------------------------------------------------------------------------------
