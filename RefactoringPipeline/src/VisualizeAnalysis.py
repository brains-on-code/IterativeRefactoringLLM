import math
import os
from itertools import product

import matplotlib.pyplot as plt
import matplotlib.colors as mcolors

import pandas as pd
import seaborn as sns
import plotly.graph_objects as go
import plotly.express as px
import numpy as np
from matplotlib.collections import LineCollection
from matplotlib.markers import MarkerStyle
from matplotlib.path import Path
from matplotlib.transforms import Affine2D
from scipy.stats import pearsonr

from sklearn.preprocessing import StandardScaler
from sklearn.decomposition import PCA
from sklearn.cluster import KMeans

import config.fields
import config.paths
import config.utils

# Setup logging
logging, handler = config.utils.setup_logger("VisualizeAnalysis")

# Define some constants for plotting data
heatmap_figsize = (8, 6)
lineplot_min = 0.8
lineplot_max = 1
combined_heatmaps_figsize = (24, 8)
format = "pdf"

# Subplots of combined plots should contain three columns. Number of rows is calculated based an n_columns respectively.
n_columns = 3

COLORS = {
    config.fields.unchanged: "#4CAF50",              # Medium green – unchanged code
    config.fields.total_insertions: "#2196F3",       # Strong blue – total insertions
    config.fields.total_deletions: "#F44336",        # Strong red – total deletions
    config.fields.modifications: "#FFC107",          # Strong amber – general modifications
    config.fields.ins_code_lines: "#2196F3",         # Same blue – inserted code lines
    config.fields.ins_comment_lines: "#64B5F6",      # Light blue – inserted comment lines
    config.fields.ins_empty_lines: "#BBDEFB",        # Very light blue – inserted empty lines
    config.fields.dels_code_lines: "#F44336",        # Same red – deleted code lines
    config.fields.dels_comment_lines: "#EF9A9A",     # Light red/pink – deleted comment lines
    config.fields.dels_empty_lines: "#FFCDD2",       # Very light red – deleted empty lines

    # Modification sub-categories (all shades of amber/orange)
    config.fields.name_change: "#FFF59D",  # sehr helles Gelb (light amber 200)
    config.fields.syntax_only: "#FFE082",  # helles Amber (amber 300)
    config.fields.comment_change: "#FFCA28",  # mittleres Amber (amber 400)
    config.fields.mixed_change: "#FB8C00",  # dunkleres Orange (orange 600)
    config.fields.semantic_change: "#FFB300",  # kräftigeres Amber (amber 600)
    "Other":               "#EF6C00",  # tiefes Orange (orange 800)

    # config.identical_partial_ast: "#FFA000",  # Mittleres Amber-Orange
    #config.value_error: "#FF6D00",  # Rötlich-orange

    # Semantic Change Subcategories (all shades of purple)
    config.fields.access_change: "#4B0082",  # Dunkles Indigo
    config.fields.call_change: "#6A0DAD",  # Klassisches Lila
    config.fields.control_change: "#7B1FA2",  # Mittelviolett
    # config.expression_wrap_change = "#8E24AA" # Optional, mittleres Lila
    config.fields.literal_change: "#9C27B0",  # Helles Lila
    config.fields.operator_change: "#BA68C8",  # Pastell-Lila
    config.fields.other_structural_change: "#D1C4E9"  # Sehr helles Lila
}

def plot_absolute_values(data, output_dir: str):
    """
    Function to plot the absolute values.
    :param data: Pandas DataFrame that contains the ABSOLUTE VALUES data!
    :param output_dir: Dir in output/ where all the results [for this LOC interval] are.
    """

    if data.empty:
        print(f"No data for absolute values plots.")
        return

    # Convert config.version to integers if possible and re-plot
    data[config.fields.version] = pd.to_numeric(data[config.fields.version], errors='coerce').astype('Int64')

    # Define distinct colors for variants
    color_palette = ['blue', 'green', 'red']
    line_styles = ['solid', 'dashed', 'dotted']
    marker_styles = ['o', '^', 's']
    marker_styles_combined = ['o', '*',
                              '^', 'v'
                              's', 'h']

    n_metrics = len(config.fields.ABSOLUTE_VALUES_METRICS)
    fig_height_per_subplot = 2.1  # Höhe je Subplot (kann angepasst werden)

    # Alle Prompts durchgehen
    for prompt_id in data[config.fields.prompt].unique():
        prompt_data = data[data[config.fields.prompt] == prompt_id]

        # Prompt-spezifisches Verzeichnis
        prompt_subdir = os.path.join(config.paths.PLOTS_SUBDIR, "prompts", str(prompt_id))
        prompt_dir = os.path.join(output_dir, prompt_subdir)
        os.makedirs(prompt_dir, exist_ok=True)

        # ----- snippet-spezifische Plots -----
        # snippet_names = prompt_data[config.snippet].unique()
        #
        # for snippet in snippet_names:
        #
        #     # Make sure snippet-specific plots land in the output folder for the specific snippet
        #     snippets_subdir = os.path.join(output_dir, config.SNIPPETS_SUBDIR, snippet, prompt_subdir)
        #     os.makedirs(snippets_subdir, exist_ok=True)
        #
        #     snippet_data = prompt_data[prompt_data[config.snippet] == snippet]
        #
        #     plt.figure(figsize=(8, fig_height_per_subplot * n_metrics))  # Dynamische Höhe
        #
        #     for i, metric in enumerate(config.ABSOLUTE_VALUES_METRICS, start=1):
        #         plt.subplot(n_metrics, 1, i)  # Dynamische Anzahl Subplots
        #         for idx, variant in enumerate(snippet_data[config.variant].unique()):
        #             variant_data = snippet_data[snippet_data[config.variant] == variant]
        #             plt.plot(variant_data[config.version], variant_data[metric], marker='o',
        #                      label=variant, color=color_palette[idx % len(color_palette)])
        #
        #         plt.title(f"{snippet} - {metric}")
        #         plt.xlabel(config.version)
        #         # Sicherstellen, dass xticks alle Versionen zeigen
        #         versions = sorted(snippet_data[config.version].dropna().unique())
        #         plt.xticks(versions, fontsize=8)
        #         plt.ylabel(metric)
        #         plt.legend(title=config.variant)
        #         plt.grid(True)
        #
        #     plt.tight_layout(pad=2)  # Padding nur einmal nach allen Subplots
        #
        #     file_name = f"{snippet}_absolute-values_{prompt_id}.{format}"
        #     plt.savefig(os.path.join(snippets_subdir, file_name))
        #     plt.close()  # Figure schließen, um Speicher freizugeben

        # Create aggregated plots across all snippets (one per variant + one combined)
        variants = ["all"] + list(prompt_data[config.fields.variant].unique())

        for variant_selection in variants:
            plt.figure(figsize=(10, math.ceil(fig_height_per_subplot * n_metrics / 2.0)))

            for i, metric in enumerate(config.fields.ABSOLUTE_VALUES_METRICS, start=1):
                plt.subplot(int(n_metrics / 2), 2, i)

                # Group data by Version and Variant, calculate mean and std for the current metric
                if variant_selection == "all":
                    aggregated_data = prompt_data.groupby([config.fields.version,config.fields.variant])[metric].agg(['mean', 'std']).reset_index()
                else:
                    # Filter by variant if not "all"
                    filtered = prompt_data[prompt_data[config.fields.variant] == variant_selection]
                    aggregated_data = filtered.groupby([config.fields.version])[metric].agg(['mean', 'std']).reset_index()

                # Plot data
                if variant_selection == "all":
                    # plot each variant separately
                    for idx, variant in enumerate(aggregated_data[config.fields.variant].unique()):
                        variant_data = aggregated_data[aggregated_data[config.fields.variant] == variant]
                        plt.plot(variant_data[config.fields.version],
                                 variant_data['mean'],
                                 label=variant,
                                 marker=marker_styles[idx % len(marker_styles)],
                                 linestyle=line_styles[idx % len(line_styles)],
                                 color=color_palette[idx % len(color_palette)])
                else:
                    # plot only this single variant
                    plt.plot(aggregated_data[config.fields.version],
                             aggregated_data['mean'],
                             marker='o',
                             label=variant_selection,
                             color=color_palette[0])

                    plt.fill_between(
                        aggregated_data[config.fields.version],
                        aggregated_data['mean'] - aggregated_data['std'],
                        aggregated_data['mean'] + aggregated_data['std'],
                        color=color_palette[0],
                        alpha=0.1)

                # for idx, variant in enumerate(aggregated_data[config.variant].unique()):
                #
                #     variant_data = aggregated_data[aggregated_data[config.variant] == variant]
                #     plt.plot(variant_data[config.version], variant_data[metric], marker='o',
                #              label=variant, color=color_palette[idx % len(color_palette)])

                if metric == 'Inline Comments':
                    plt.ylim(bottom=-0.25)

                # Titles and labels
                #plt.title(f"#{metric}")
                plt.xlabel(config.fields.version, fontsize=12)
                plt.xticks(sorted(aggregated_data[config.fields.version].dropna().unique()), fontsize=12)
                plt.ylabel("#" + metric, fontsize=12)

                # remove top and right spines for cleaner look
                plt.gca().spines['top'].set_visible(False)
                plt.gca().spines['right'].set_visible(False)
                plt.grid(False)

            if variant_selection == "all":
                plt.legend(
                    title="", # config.fields.variant
                    labels=[r'$Variant_{Original}$', r'$Variant_{Meaning}$', r'$Variant_{NoComment}$'], # TODO fix this hardcoding
                    #loc="upper center",  # Legende oberhalb des unteren Rands
                    # bbox_to_anchor=(, -0.25),  # zentriert, etwas unterhalb
                    #ncol=3,  # Anzahl Spalten (nebeneinander)
                    frameon=False,  # optional: ohne Rahmen
                    fontsize=11,  # Schriftgröße der Legendeneinträge
                    title_fontsize=11,  # Schriftgröße des Legendentitels
                )

            plt.tight_layout(pad=1)  # Adjust padding

            # Save the aggregated plot
            filename = f"absolute_values_across_all-snippets_{prompt_id}_{variant_selection}.{format}"
            plt.savefig(os.path.join(prompt_dir, filename))
            plt.close()

        print(
            f"Saved: ({prompt_id}) plots for absolute values in ‘{prompt_dir}’.")

    # ----- NEW: Global Plot comparing prompts within each variant -----
    globals_dir = os.path.join(output_dir, config.paths.PLOTS_SUBDIR, "global")
    os.makedirs(globals_dir, exist_ok=True)

    plt.figure(figsize=(8, fig_height_per_subplot * n_metrics))

    for i, metric in enumerate(config.fields.ABSOLUTE_VALUES_METRICS, start=1):
        plt.subplot(n_metrics, 1, i)

        # Gruppiere über Version, Variant UND Prompt → Mittelwerte über Snippets
        aggregated_data = data.groupby([config.fields.version, config.fields.variant,
                                        config.fields.prompt])[metric].mean().reset_index()

        # Iteriere über Kombinationen von Variant & Prompt
        for idx, (variant, variant_df) in enumerate(aggregated_data.groupby(config.fields.variant)):
            for jdx, (prompt, prompt_df) in enumerate(variant_df.groupby(config.fields.prompt)):
                plt.plot(
                    prompt_df[config.fields.version],
                    prompt_df[metric],
                    marker='o',
                    color=color_palette[idx % len(color_palette)],  # Farbe nach Variante
                    linestyle=line_styles[jdx % len(line_styles)],  # Stil nach Prompt
                    label=f"{variant}-{prompt}"
                )

        plt.title(f"Average {metric} Across All Snippets (Variants × Prompts)")
        plt.xlabel(config.fields.version, fontsize=12)
        plt.xticks(sorted(aggregated_data[config.fields.version].dropna().unique()), fontsize=10)
        plt.ylabel(metric, fontsize=12)
        plt.legend(title="Variant-Prompt", fontsize=10,
                   loc="center left",
                   bbox_to_anchor=(1.0, 0.5))
        plt.grid(True)

    plt.tight_layout(pad=2)
    filename = f"absolute_values_across_all-snippets_GLOBAL.{format}"
    plt.savefig(os.path.join(globals_dir, filename))
    plt.close()

    print(f"Saved: (GLOBAL) The combined variant-prompt plots in ‘{globals_dir}’.")

    # ----- NEW: Pairwise Prompt Comparisons -----
    # Wir wollen nur bestimmte Prompt-Paare vergleichen
    prompt_pairs = [
        ("pKF0", "pKF1"),
        ("pKF0", "pKF2")
    ]

    # Definiere Linienstile für die Prompts
    prompt_styles = ["solid", "dashed", "dotted"]

    for pair in prompt_pairs:
        plt.figure(figsize=(8, fig_height_per_subplot * n_metrics))

        for i, metric in enumerate(config.fields.ABSOLUTE_VALUES_METRICS, start=1):
            plt.subplot(n_metrics, 1, i)

            # Aggregiere über Snippets → Mittelwerte pro Version/Variante/Prompt
            aggregated_data = data.groupby([config.fields.version, config.fields.variant,
                                            config.fields.prompt])[metric].mean().reset_index()

            # Nur die relevanten Prompts behalten
            pair_data = aggregated_data[aggregated_data[config.fields.prompt].isin(pair)]

            # Iteriere über Varianten
            for idx, (variant, variant_df) in enumerate(pair_data.groupby(config.fields.variant)):
                for jdx, prompt in enumerate(pair):
                    prompt_df = variant_df[variant_df[config.fields.prompt] == prompt]
                    if prompt_df.empty:
                        continue
                    plt.plot(
                        prompt_df[config.fields.version],
                        prompt_df[metric],
                        color=color_palette[idx % len(color_palette)],  # Farbe nach Variante
                        linestyle=prompt_styles[idx % len(prompt_styles)],  # Stil nach Prompt
                        marker=marker_styles_combined[jdx % len(marker_styles_combined)],
                        label=f"{variant}-{prompt}"
                    )

            #plt.title(f"Average {metric} Across All Snippets ({pair[0]} vs {pair[1]})")
            if (i == n_metrics):
                plt.xlabel(config.fields.version, fontsize=12)
            else:
                plt.xlabel("")

            # remove top and right spines for cleaner look
            plt.gca().spines['top'].set_visible(False)
            plt.gca().spines['right'].set_visible(False)
            plt.grid(False)

            plt.xticks(sorted(pair_data[config.fields.version].dropna().unique()), fontsize=10)
            plt.ylabel(metric, fontsize=12)

        plt.legend(
            title="Variant-Prompt",
            fontsize=12,
            labels=[
                r'$Variant_{Original}$ - $Prompt_{Original}$',
                r'$Variant_{Original}$ - $Prompt_{Meaning}$',
                r'$Variant_{Meaningless}$ - $Prompt_{Original}$',
                r'$Variant_{Meaningless}$ - $Prompt_{Meaning}$',
                r'$Variant_{NoComment}$ - $Prompt_{Original}$',
                r'$Variant_{NoComment}$ - $Prompt_{Meaning}$'], # TODO fix this hardcoding
            ncol=2,
            loc="upper center",  # Legende oberhalb des unteren Rands
            bbox_to_anchor=(0.5, -0.45),  # zentriert, etwas unterhalb
            )

        plt.tight_layout()
        filename = f"absolute_values_across_all-snippets_{pair[0]}_vs_{pair[1]}.{format}"
        plt.savefig(os.path.join(globals_dir, filename), bbox_inches='tight')
        plt.close()

        print(f"Saved: (GLOBAL) The pairwise plots for {pair[0]} vs {pair[1]} in ‘{globals_dir}’.")

################################################################################################

def plot_horizontal_data(data, output_dir : str):
    """
        Function to plot the horizontal analysis data.
        :param data: Pandas DataFrame that contains the data.
        """

    if data.empty:
        print(f"No data for horizontal plots.")
        return    # Define number of rows and columns for combined plots

    n_rows = math.ceil(len(config.fields.AVG_METRICS) / n_columns)

    # Extract all unique versions from both columns
    versions = sorted(set(data[config.fields.version1]).union(set(data[config.fields.version2])))

    # Collect all unique variants
    variants = sorted(data[config.fields.variant].unique())

    # Alle Prompts durchgehen
    for prompt_id in data[config.fields.prompt].unique():

        prompt_data = data[data[config.fields.prompt] == prompt_id]

        # Prompt-spezifisches Verzeichnis
        prompt_subdir = os.path.join(config.paths.PLOTS_SUBDIR, "prompts", str(prompt_id), "horizontal_plots")
        prompt_dir = os.path.join(output_dir, prompt_subdir)
        os.makedirs(prompt_dir, exist_ok=True)

        # TODO: maybe erweitern, aktuell nur avg sim score geplottet
        _plot_horizontal_heatmaps(n_rows, prompt_data, prompt_dir, prompt_id, variants, versions)

        print(f"Saved: {prompt_id} The plots for the horizontal analysis in ‘{prompt_dir}’.")


def _plot_horizontal_heatmaps(n_rows, prompt_data, prompt_dir, prompt_id, variants, versions):
    """
    Generate one similarity heatmap (config.avg_similarity_score) per variant for a given prompt.

    Args:
        n_rows (int): Number of rows for subplot grid (currently unused, but kept for compatibility).
        prompt_data (pd.DataFrame): Data with similarity scores per version pair and variant.
        prompt_dir (str): Directory where prompt results are stored.
        prompt_id (str): Identifier of the prompt.
        variants (list[str]): List of variants to plot.
        versions (list[str]): List of versions to include.
    """

    # Create subdir for these plots
    plots_dir = os.path.join(prompt_dir, "heatmaps")
    os.makedirs(plots_dir, exist_ok=True)

    # Create complete combinations of versions and variants
    horizontally_relevant_cols = [config.fields.version1, config.fields.version2, config.fields.variant]
    all_combinations = pd.DataFrame(
        list(product(versions, versions, variants)), columns=horizontally_relevant_cols
    )

    # Merge original data with the complete combinations -> fill missing with 0
    prompt_data = pd.merge(
        all_combinations, prompt_data, on=horizontally_relevant_cols, how='left'
    ).fillna(1)

    avg_simscore = "avg_" + config.fields.avg_similarity_score

    # Generate one heatmap per variant
    for variant in variants:
        subset = prompt_data[prompt_data[config.fields.variant] == variant]

        # Pivot for avg similarity score
        pivot_table = subset.pivot_table(
            index=config.fields.version2,
            columns=config.fields.version1,
            values=avg_simscore,
            dropna=False
        ).reindex(
            index=sorted(versions, reverse=True),
            columns=sorted(versions),
            fill_value=0
        )

        ax = plot_heatmap(avg_simscore, pivot_table)

        #plt.title(f"{config.fields.avg_similarity_score} Heatmap for {config.fields.variant} {variant} ({prompt_id})", fontsize=12)
        plt.xlabel(config.fields.version1, fontsize=12)
        plt.ylabel(config.fields.version2, fontsize=12)

        # Tick-Label-Größen
        plt.xticks(fontsize=12)
        plt.yticks(fontsize=12)

        # Legende (Colorbar)
        cbar = ax.collections[0].colorbar
        cbar.ax.tick_params(labelsize=12)

        # Save heatmap
        plot_path = os.path.join(plots_dir, f"avg_simscore_heatmap_{variant}_{prompt_id}.{format}")
        plt.tight_layout()
        plt.savefig(plot_path, bbox_inches="tight")
        plt.close()



################################################################################################

def plot_vertical_data(data, output_dir):
    """
    Function to plot the vertical analysis data.
    :param data: Pandas DataFrame that contains the data.
    """

    if data.empty:
        print(f"No data for vertical plots.")
        return

    # Define number of rows and columns for combined plots
    n_rows = math.ceil(len(config.fields.AVG_METRICS) / n_columns)

    # Extract all unique variants from both columns
    variants = sorted(set(data[config.fields.variant1]).union(set(data[config.fields.variant2])))

    # Collect all unique versions
    versions = sorted(data[config.fields.version].unique())

    # Alle Prompts durchgehen
    for prompt_id in data[config.fields.prompt].unique():

        prompt_data = data[
            (data[config.fields.prompt] == prompt_id) | (data[config.fields.prompt] == config.fields.no_prompt)
            ]

        # Prompt-spezifisches Verzeichnis
        prompt_subdir = os.path.join(config.paths.PLOTS_SUBDIR, "prompts", str(prompt_id), "vertical_plots")
        prompt_dir = os.path.join(output_dir, prompt_subdir)
        os.makedirs(prompt_dir, exist_ok=True)

        _plot_similarity_lineplot_vertical(prompt_data, prompt_id, prompt_dir)

        # TODO: maybe erweitern, aktuell nur avg sim score geplottet
        _plot_vertical_heatmaps(n_rows, prompt_data, prompt_dir, prompt_id, versions, variants)

        print(f"Saved: ({prompt_id}) in ‘{prompt_dir}’.")


def _plot_vertical_heatmaps(n_rows, prompt_data, prompt_dir, prompt_id, versions, variants):
    """
    Generate one similarity heatmap (config.avg_similarity_score) per version for a given prompt.

    Args:
        n_rows (int): Number of rows for subplot grid (currently unused, kept for compatibility).
        prompt_data (pd.DataFrame): Data with similarity scores per variant pair and version.
        prompt_dir (str): Directory where prompt results are stored.
        prompt_id (str): Identifier of the prompt.
        versions (list[str]): List of versions to plot.
        variants (list[str]): List of variants to include.
    """

    # Create subdir for these plots
    plots_dir = os.path.join(prompt_dir, "heatmaps")
    os.makedirs(plots_dir, exist_ok=True)

    # Create complete combinations of variants and versions
    vertically_relevant_cols = [config.fields.variant1, config.fields.variant2, config.fields.version]
    all_combinations = pd.DataFrame(
        list(product(variants, variants, versions)), columns=vertically_relevant_cols
    )

    # Merge original data with the complete combinations -> fill missing with 0
    prompt_data = pd.merge(
        all_combinations, prompt_data, on=vertically_relevant_cols, how='left'
    ).fillna(1)

    avg_simscore = "avg_" + config.fields.avg_similarity_score

    # Generate one heatmap per version
    for version in versions:
        subset = prompt_data[prompt_data[config.fields.version] == version]

        # Pivot for avg similarity score
        pivot_table = subset.pivot_table(
            index=config.fields.variant2,
            columns=config.fields.variant1,
            values= avg_simscore,
            dropna=False
        ).reindex(
            index=sorted(variants, reverse=True),
            columns=sorted(variants),
            fill_value=0
        )

        ax = plot_heatmap(avg_simscore, pivot_table)

        plt.title(f"{config.fields.avg_similarity_score} Heatmap for {config.fields.version} {version} ({prompt_id})", fontsize=12)
        plt.xlabel(config.fields.variant1, fontsize=12)
        plt.ylabel(config.fields.variant2, fontsize=12)

        # Tick-Label-Größen
        plt.xticks(fontsize=12)
        plt.yticks(fontsize=12)
        plt.tight_layout()

        # Legende (Colorbar)
        cbar = ax.collections[0].colorbar
        cbar.ax.tick_params(labelsize=12)
        plt.tight_layout()

        # Save heatmap
        plot_path = os.path.join(plots_dir, f"avg_simscore_heatmap_v{version}_{prompt_id}.{format}")
        plt.savefig(plot_path)
        plt.close()


def plot_heatmap(avg_simscore, pivot_table):
    """Helper function that is needed in both horizontal and vertical heatmap creation."""

    plt.figure(figsize=(6, 3))
    # Create a boolean mask to hide the right triangular half of the heatmap:
    # 1. np.ones_like(pivot_table, dtype=bool) → generate a boolean matrix of the same shape as pivot_table, filled with True.
    # 2. np.triu(..., k=1) → keep only the upper triangle above the main diagonal (k=1 excludes the diagonal itself), rest becomes False.
    # 3. [:, ::-1] → reverse the column order, effectively mirroring the upper triangle horizontally (right-to-left).
    # 4. ~ (...) → invert the mask, because seaborn interprets True as "mask (hide)" and False as "show".
    #    After inversion, the right half of the heatmap is masked (hidden), while the left half remains visible.
    mask = ~np.triu(np.ones_like(pivot_table, dtype=bool), k=1)[:, ::-1]

    return sns.heatmap(
        pivot_table,
        annot=True,
        annot_kws={"size": 14},   # Zahlen in den Feldern größer,
        fmt=".2f",
        cmap="Greens",
        cbar_kws={'label': ""},
        mask=mask
    )


def _plot_similarity_lineplot_vertical(data, prompt_id, output_dir):
    """
    Create a line plot of average similarity scores across versions,
    with one line per variant pair.

    Args:
        data (pd.DataFrame): Input dataframe with columns
            [config.version, config.variant1, config.variant2, config.avg_similarity_score].
        prompt_id (str): Identifier for the current prompt.
        output_dir (str): Directory where plot should be saved.
    """

    data = data.copy()

    # Ensure version is integer
    data[config.fields.version] = pd.to_numeric(data[config.fields.version], errors="coerce").astype("Int64")

    # Create variant pair column
    data["VariantPair"] = data[config.fields.variant1] + "-" + data[config.fields.variant2]

    avg_simscore = "avg_" + config.fields.avg_similarity_score

    color_palette = ['blue', 'green', 'red']
    line_styles = ['solid', 'dashed', 'dotted']
    marker_styles = ['o', '^', 's']

    line_colors_map = {
        "KF0-KF1": ['blue', 'green'],
        "KF0-KF2": ['blue', 'red'],
        "KF1-KF2": ['green', 'red']
    }

    # Initialize plot
    fig = plt.figure(figsize=(8, 3))

    # Custom combined markers
    dx = 0.8  # horizontal separation in marker coordinate units (≈ marker radius = 1)
    s = 1   # scale each sub-marker inside the composite (0..1)

    circle = Path.unit_circle()
    circle = circle.transformed(Affine2D().scale(s).translate(-dx, 0.0))
    left_circle_marker = MarkerStyle(circle)

    tri_base = MarkerStyle('^')
    triangle = tri_base.get_path().transformed(tri_base.get_transform())
    left_triangle = triangle.transformed(Affine2D().scale(s).translate(-dx, 0.0))
    left_triangle_marker = MarkerStyle(left_triangle)
    right_triangle = triangle.transformed(Affine2D().scale(s).translate(+dx, 0.0))
    right_triangle_marker = MarkerStyle(right_triangle)

    square_base = MarkerStyle('s')
    square = square_base.get_path().transformed(square_base.get_transform())
    right_square_marker = square.transformed(Affine2D().scale(s).translate(+dx, 0.0))
    right_square_marker = MarkerStyle(right_square_marker)

    marker_map = {
        "KF0-KF1": [left_circle_marker, right_triangle_marker],
        "KF0-KF2": [left_circle_marker, right_square_marker],
        "KF1-KF2": [left_triangle_marker, right_square_marker]
    }

    # Plot each variant pair using matplotlib
    variant_pairs = data["VariantPair"].unique()
    for idx, variant_pair in enumerate(variant_pairs):
        pair_data = data[data["VariantPair"] == variant_pair]
        print(variant_pair)

        # --- 1) Alternating-color line segments with subdivisions ---
        pts = np.column_stack((pair_data[config.fields.version].values, pair_data[avg_simscore].values))
        
        # Get colors from line_colors_map, or use fallback
        segment_colors = line_colors_map.get(variant_pair, ['blue', 'green'])
        
        # Create multiple segments between each pair of consecutive datapoints
        n_subdivisions = 8
        gap_ratio = 0.05  # 5% gap between segments
        all_segs = []
        all_colors = []
        
        for i in range(len(pts) - 1):
            p1 = pts[i]
            p2 = pts[i + 1]
            
            # Create n_subdivisions intermediate points (including endpoints)
            t_values = np.linspace(0, 1, n_subdivisions + 1)
            interpolated_points = np.array([p1 + (p2 - p1) * t for t in t_values])
            
            # Create segments between consecutive interpolated points with gaps
            for j in range(len(interpolated_points) - 1):
                t_start = t_values[j]
                t_end = t_values[j + 1]
                segment_length = t_end - t_start
                gap_size = segment_length * gap_ratio / 2
                
                # Shorten segment by gap_size on both ends
                t_start_adj = t_start + gap_size
                t_end_adj = t_end - gap_size
                
                if t_start_adj < t_end_adj:  # Only add segment if there's still length after gap
                    p_start = p1 + (p2 - p1) * t_start_adj
                    p_end = p1 + (p2 - p1) * t_end_adj
                    seg = np.array([p_start, p_end])
                    all_segs.append(seg)
                    all_colors.append(segment_colors[j % 2])
        
        segs = np.array(all_segs)
        colors = all_colors
        
        # Map colors to linestyles: green = dashed, red = dotted, others = solid
        linestyles = []
        for color in colors:
            if color == 'green':
                linestyles.append('--')
            elif color == 'red':
                linestyles.append(':')
            else:
                linestyles.append('-')

        lc = LineCollection(segs, colors=colors, linestyles=linestyles, linewidth=1, zorder=1)
        plt.gca().add_collection(lc)

        # Get correct markers from marker_map
        markers = marker_map.get(variant_pair, [left_circle_marker, right_triangle_marker])
        
        plt.plot(
            pair_data[config.fields.version],
            pair_data[avg_simscore],
            marker=markers[0],
            linestyle='none',
            color=segment_colors[0],
            label=variant_pair
        )
        plt.plot(
            pair_data[config.fields.version],
            pair_data[avg_simscore],
            marker=markers[1],
            linestyle='none',
            color=segment_colors[1]
        )

    # remove top and right spines for cleaner look
    plt.gca().spines['top'].set_visible(False)
    plt.gca().spines['right'].set_visible(False)
    plt.grid(False)

    #plt.title(f"Average Similarity Score across Versions ({prompt_id})")
    plt.xlabel("Version", fontsize=12)
    plt.ylabel("Average Similarity Score", fontsize=12)
    plt.ylim(lineplot_min, lineplot_max)

    # Restrict x-axis to integer ticks only
    versions = sorted(data[config.fields.version].dropna().unique())
    plt.xticks(versions, fontsize=10)

    # Create custom legend with line styles
    from matplotlib.lines import Line2D
    legend_elements = []
    for variant_pair in data["VariantPair"].unique():
        pair_colors = line_colors_map.get(variant_pair, ['blue', 'green'])
        # Create line element showing both colors with their respective line styles
        legend_elements.append(
            Line2D([0], [0], 
                   color=pair_colors[0], 
                   linestyle='--' if pair_colors[0] == 'green' else ':' if pair_colors[0] == 'red' else '-',
                   linewidth=2,
                   label=variant_pair)
        )
    
    plt.legend(
        handles=legend_elements,
        title="Variant Pair",
        bbox_to_anchor=(1.05, 1),
        loc="upper left",
        fontsize=12)

    # Save plot
    plot_path = os.path.join(output_dir, f"avg_simscore_lineplot_{prompt_id}.{format}")
    plt.tight_layout()
    plt.savefig(plot_path, bbox_inches="tight")
    plt.close()

# -------------------Helper Functions for Stacked Bar Plots---------------------------------------------------------

def prepare_data(detailed, diff_data):
    """
    Prepares the dataframe given the detailed-option to either include:
    100 % = [unchanged, modifications, total insertions, total deletions]
    or
    100% = [unchanged, modifications, *detailed insertions and deletions columns*]

    :param detailed: True if we want the detailed visualization, False otherwise.
    :param diff_data: The dataframe with the data to be visualized.
    :return: the prepared and grouped data + filtered columns
    """
    data = diff_data.copy()

    if detailed:
        category_cols = config.fields.DETAILED_CHANGE_COLS
        data = data.drop(columns=[config.fields.modifications,
                                  config.fields.total_insertions,
                                  config.fields.total_deletions]
                         )  # TODO: ja das ist irgendwie noch hässlich aber funktioniert so.

    else:
        category_cols = config.fields.MAIN_CHANGE_COLS

    # for col in category_cols:
    #     data[col] = data[col].str.rstrip('%').astype(float) / 100

    return category_cols, data


# ------------------------------------------------------------------------------------------
def create_plot_from_subset(category_cols, subset, figsize=(12, 6)):
    subset = subset.copy()
    subset = subset.sort_values("Transition")

    fig, ax = plt.subplots(figsize=figsize, constrained_layout=True)
    left = [0] * len(subset)

    # Draw bars
    for col in category_cols:
        values = subset[col].values
        ax.barh(subset["Transition"], values, left=left, label=col, color=COLORS[col])
        left = [i + j for i, j in zip(left, values)]

    # Draw percentage values per bar
    threshold = 0.02  # From when a segment is considered "small"
    offset = 0.5  # Distance of the labeling downwards

    for idx, row in subset.iterrows():
        x_start = 0
        for col in category_cols:
            width = row[col]
            label = f"{width * 100:.0f}%"

            # if label != "0%":
            x_center = x_start + width / 2
            y_index = subset.index.get_loc(idx)

            if width >= threshold:
                # Large block → Label centered in the block
                ax.text(
                    x_center,
                    y_index,
                    label,
                    ha='center',
                    va='center',
                    fontsize=12,
                    color='black'
                )
            # else:
            # Small block → Label below + line
            # ax.text(
            #     x_center,
            #     y_index - offset,
            #     label,
            #     ha='center',
            #     va='top',
            #     fontsize=8,
            #     color='black'
            # )
            #
            # # Line from the center of the segment to the label
            # ax.plot(
            #     [x_center, x_center],  # x: vertical line
            #     [y_index, y_index - offset + 0.05],  # y: slightly up to the label
            #     color='black',
            #     linewidth=0.5
            # )

            x_start += width

    # Fontsize for values on the axes
    ax.tick_params(axis="both", labelsize=12)

    return ax


# ---------------Main Functions-------------------------------------------------------

# def plot_horizontal_stacked_change_distribution_per_snippet(diff_data, detailed, output_dir, prompt_subdir):
#     """
#     Plots the distribution of types of changes (modifications, insertions, deletions, ...) per snippet per variant.
#
#     :param diff_data: The dataframe with data from evaluating metrics' diffs.
#     :param detailed: Whether we want to visualize a more detailed stacked distribution.
#     :param output_dir: The folder where the evaluations and visualizations go.
#     """
#
#     if diff_data.empty:
#         print(f"No data for variant's stacked plots.")
#         return
#
#     category_cols, data = prepare_data(detailed, diff_data)
#
#     data["Transition"] = "v" + data[config.version1].astype(str) + " → " + "v" + data[config.version2].astype(str)
#
#     # Group by variant and snippet, so we'll have one plot per variant per snippet
#     groups = data.groupby([config.variant, config.snippet])
#
#     for (variant, snippet), subset in groups:
#
#         ax = create_plot_from_subset(category_cols, subset)
#
#         # Plot anpassen
#         ax.set_title(f"Code Changes for {config.snippet} {snippet} ({config.variant} {variant})")
#         ax.set_xlabel("Proportion")
#         ax.set_xlim(0, 1)
#         ax.invert_yaxis()
#         ax.legend(title="Change Type", loc="center left", bbox_to_anchor=(1.0, 0.5))
#
#         # Save plot in respective snippets subdir
#         snippets_subdir = os.path.join(output_dir, config.SNIPPETS_SUBDIR, snippet, prompt_subdir)
#         os.makedirs(snippets_subdir, exist_ok=True)
#
#         if detailed:
#             safe_filename = f"{snippet}_{variant}_detailed_stacked_bar".replace(" ", "_").replace("→", "to")
#         else:
#             safe_filename = f"{snippet}_{variant}_stacked_bar".replace(" ", "_").replace("→", "to")
#
#         filepath = os.path.join(snippets_subdir, f"{safe_filename}.{format}")
#         plt.savefig(filepath, dpi=300, bbox_inches='tight')
#         plt.close()
#
#         print(f"Saved plot: {filepath}.")
#
#
#
# def plot_vertical_stacked_change_distribution_per_snippet(diff_data, detailed, output_dir, prompt_subdir):
#     """
#     Plots the distribution of types of changes (modifications, insertions, deletions, ...) per snippet per version.
#
#     :param diff_data: The dataframe with data from evaluating metrics' diffs.
#     :param detailed: Whether we want to visualize a more detailed stacked distribution.
#     :param output_dir: The folder where the evaluations and visualizations go.
#     """
#
#     if diff_data.empty:
#         print(f"No data for version's stacked plots.")
#         return
#
#     category_cols, data = prepare_data(detailed, diff_data)
#
#     data["Transition"] = data[config.variant1].astype(str) + " → " + data[config.variant2].astype(str)
#
#     # Group by version and snippet, so we'll have one plot per version per snippet
#     groups = data.groupby([config.version, config.snippet])
#
#     for (version, snippet), subset in groups:
#
#         ax = create_plot_from_subset(category_cols, subset)
#
#         # Plot anpassen
#         ax.set_title(f"Code Changes for {config.snippet} {snippet} ({config.version} {version})")
#         ax.set_xlabel("Proportion")
#         ax.set_xlim(0, 1)
#         ax.invert_yaxis()
#         ax.legend(title="Change Type", loc="center left", bbox_to_anchor=(1.0, 0.5))
#
#         # Save plot in respective snippets subdir
#         snippets_subdir = os.path.join(output_dir, config.SNIPPETS_SUBDIR, snippet, prompt_subdir)
#         os.makedirs(snippets_subdir, exist_ok=True)
#
#         if detailed:
#             safe_filename = f"{snippet}_v{version}_detailed_stacked_bar".replace(" ", "_").replace("→", "to")
#         else:
#             safe_filename = f"{snippet}_v{version}_stacked_bar".replace(" ", "_").replace("→", "to")
#
#         filepath = os.path.join(snippets_subdir, f"{safe_filename}.{format}")
#         plt.savefig(filepath, dpi=300, bbox_inches='tight')
#         plt.close()
#
#         print(f"Saved plot: {filepath}.")


# ------------------------------------------------------------------------------------------
def plot_average_horizontal_stacked_change_distribution(diff_data, prompt_id, detailed, output_dir):
    """
    Plots the change distribution within one variant averaged across all snippet.

    :param diff_data: The dataframe with data from evaluating metrics' diffs.
    :param detailed: Whether we want to visualize a more detailed stacked distribution.
    :param output_dir: The folder where the evaluations and visualizations go.
    """

    if diff_data.empty:
        print(f"No data for variant's averaged stacked plots.")
        return

    # Create subdir for these plots
    plots_dir = os.path.join(output_dir, "horizontal_plots", "stacked_plots")
    os.makedirs(plots_dir, exist_ok=True)

    category_cols, data = prepare_data(detailed, diff_data)

    data["Transition"] = "v" + data[config.fields.version1].astype(str) + " → " + "v" + data[
        config.fields.version2].astype(str)

    # Group by variant and transition + calculate mean of groups
    # Reset index makes sure we have variant and transition columns again
    groups_average_mean = data.groupby([config.fields.variant, "Transition"])[category_cols].mean().reset_index()

    # Now we can group by only variant, so we have one plot per variant, averaged over all snippets
    variant_average_groups = groups_average_mean.groupby(config.fields.variant)

    for variant, subset in variant_average_groups:
        ax = create_plot_from_subset(category_cols, subset, (12, 5))

        #ax.set_title(f"Average Change Distribution for {config.fields.variant} {variant} ({prompt_id})", fontsize=12)
        ax.set_xlabel("Proportion of Change Types", fontsize=12)
        ax.set_ylabel("Version Transition", fontsize=12)
        ax.set_xlim(0, 1)
        ax.invert_yaxis()
        ax.legend(
            title="Change Type",
            loc="upper center",  # Legende oberhalb des unteren Rands
            bbox_to_anchor=(0.5, -0.25),  # zentriert, etwas unterhalb
            ncol=4,  # Anzahl Spalten (nebeneinander)
            #frameon=False,  # optional: ohne Rahmen
            fontsize=12,  # Schriftgröße der Legendeneinträge
            title_fontsize=12,  # Schriftgröße des Legendentitels
        )

        # Save plot
        if detailed:
            safe_filename = f"{variant}_{prompt_id}_detailed_average_changes".replace(" ", "_").replace("→", "to")
        else:
            safe_filename = f"{variant}_{prompt_id}_average_changes".replace(" ", "_").replace("→", "to")

        filepath = os.path.join(plots_dir, f"{safe_filename}.{format}")
        #plt.tight_layout()
        plt.savefig(filepath, dpi=300, bbox_inches="tight")
        plt.close()

    if detailed:
        logging.info(f"Saved: The plots for {config.fields.variant}'s detailed average change distribution per variant in ‘{plots_dir}’.")
    else:
        logging.info(f"Saved: The plots for {config.fields.variant}'s average change distribution per variant in ‘{plots_dir}’.")


def plot_average_vertical_stacked_change_distribution(diff_data, prompt_id, detailed, output_dir):
    """
    Plots the change distribution within one version averaged across all variants.

    :param diff_data: The dataframe with data from evaluating metrics' diffs.
    :param detailed: Whether we want to visualize a more detailed stacked distribution.
    :param output_dir: The folder where the evaluations and visualizations go.
    """
    if diff_data.empty:
        print(f"No data for version's averaged stacked plots.")
        return

    # Create subdir for these plots
    plots_dir = os.path.join(output_dir, "vertical_plots", "stacked_plots")
    os.makedirs(plots_dir, exist_ok=True)

    category_cols, data = prepare_data(detailed, diff_data)

    data["Transition"] = data[config.fields.variant1].astype(str) + " → " + data[config.fields.variant2].astype(str)

    # Group by variant and transition + calculate mean of groups
    # Reset index makes sure we have variant and transition columns again
    groups_average_mean = data.groupby([config.fields.version, "Transition"])[category_cols].mean().reset_index()

    # Now we can group by only variant, so we have one plot per variant, averaged over all snippets
    version_average_groups = groups_average_mean.groupby(config.fields.version)

    for version, subset in version_average_groups:
        ax = create_plot_from_subset(category_cols, subset, (12, 5))

        #ax.set_title(f"Average Change Distribution for {config.fields.version} {version} ({prompt_id})", fontsize=12)
        ax.set_xlabel("Proportion of Change Types", fontsize=12)
        ax.set_ylabel("Variant Pair", fontsize=12)

        # manually replace y-tick labels
        ax.set_yticklabels(['Original→Meaningless', 'Original→NoComment', 'Meaningless→NoComment']) # todo: fix this hardcoding

        ax.set_xlim(0, 1)
        ax.invert_yaxis()

        ax.legend(
            title="Change Type:",
            loc="upper center",  # Legende oberhalb des unteren Rands
            bbox_to_anchor=(0.4, -0.35),  # zentriert, etwas unterhalb
            ncol=4,  # Anzahl Spalten (nebeneinander)
            #frameon=False  # optional: ohne Rahmen
            fontsize=12,  # Schriftgröße der Legendeneinträge
            title_fontsize=12,  # Schriftgröße des Legendentitels
        )

        # Save plot
        if detailed:
            safe_filename = f"v{version}_detailed_average_changes".replace(" ", "_").replace("→", "to")
        else:
            safe_filename = f"v{version}_average_changes".replace(" ", "_").replace("→", "to")

        filepath = os.path.join(plots_dir, f"{safe_filename}.{format}")
        plt.tight_layout()
        plt.savefig(filepath, dpi=300, bbox_inches="tight")
        plt.close()

    if detailed:
        print(
            f"Saved: {config.fields.version}'s detailed average change distribution per variant in ‘{plots_dir}’.")
    else:
        print(f"Saved: {config.fields.version}'s average change distribution per variant in ‘{plots_dir}’.")


# ##############################################################################################
def plot_all_horizontal_stacked_change_distributions(diff_data, output_dir):

    # Alle Prompts durchgehen
    for prompt_id in diff_data[config.fields.prompt].unique():
        prompt_data = diff_data[diff_data[config.fields.prompt] == prompt_id]

        # Prompt-spezifisches Verzeichnis
        prompt_subdir = os.path.join(config.paths.PLOTS_SUBDIR, "prompts", str(prompt_id))
        plots_dir = os.path.join(output_dir, prompt_subdir)

        # Create (detailed) stacked bar plots per snippet
        # plot_horizontal_stacked_change_distribution_per_snippet(diff_data=prompt_data, detailed=False, output_dir=output_dir, prompt_subdir=prompt_subdir)
        # plot_horizontal_stacked_change_distribution_per_snippet(diff_data=prompt_data, detailed=True, output_dir=output_dir, prompt_subdir=prompt_subdir)

        # Create (detailed) stacked bar plots averaged across all snippets
        plot_average_horizontal_stacked_change_distribution(diff_data=prompt_data, prompt_id=prompt_id, detailed=False, output_dir=plots_dir)
        plot_average_horizontal_stacked_change_distribution(diff_data=prompt_data, prompt_id=prompt_id, detailed=True, output_dir=plots_dir)


def plot_all_vertical_stacked_change_distributions(diff_data, output_dir):

    # Alle Prompts durchgehen
    for prompt_id in diff_data[config.fields.prompt].unique():
        prompt_data = diff_data[diff_data[config.fields.prompt] == prompt_id]

        # Prompt-spezifisches Verzeichnis
        prompt_subdir = os.path.join(config.paths.PLOTS_SUBDIR, "prompts", str(prompt_id))
        plots_dir = os.path.join(output_dir, prompt_subdir)

        # Create (detailed) stacked bar plots per snippet
        # plot_vertical_stacked_change_distribution_per_snippet(diff_data=prompt_data, detailed=False, output_dir=output_dir, prompt_subdir=prompt_subdir)
        # plot_vertical_stacked_change_distribution_per_snippet(diff_data=prompt_data, detailed=True, output_dir=output_dir, prompt_subdir=prompt_subdir)

        # Create (detailed) stacked bar plots averaged across all snippets
        plot_average_vertical_stacked_change_distribution(diff_data=prompt_data, prompt_id=prompt_id, detailed=False, output_dir=plots_dir)
        plot_average_vertical_stacked_change_distribution(diff_data=prompt_data, prompt_id=prompt_id, detailed=True, output_dir=plots_dir)


# ##############################################################################################


# ##############################################################################################

# Hilfsfunktion: Leicht abgedunkelten Farbton erzeugen
def shade_color(color, factor=0.7):
    rgb = mcolors.to_rgb(color)
    shaded = [min(1, c * factor + 0.3 * (1 - factor)) for c in rgb]  # heller/dunkler mischen
    return f"rgb({int(shaded[0] * 255)},{int(shaded[1] * 255)},{int(shaded[2] * 255)})"

def sankey_plot(df, output_dir):
    """
    Erstellt Sankey-Diagramme aus den bereits normalisierten Prozentwerten pro Change-Type.
    Erwartet Spalten wie: Variant, Prompt, Version 1, Version 2, Rename, SyntaxOnly, ...
    """

    variants = df['Variant'].unique()
    df = df.copy()

    # Relevante Change-Type Spalten (alle, die in config.MODIFICATIONS_COLS stehen)
    change_types = config.fields.MODIFICATIONS_COLS

    for variant in variants:
        df_var = df[df['Variant'] == variant]
        prompts = df_var['Prompt'].unique()

        for prompt in prompts:
            df_sub = df_var[df_var['Prompt'] == prompt]

            # --- Links vorbereiten ---
            rows = []
            for _, row in df_sub.iterrows():
                version_label = f"v{int(row['Version 2'])}"
                for ct in change_types:
                    value = row[ct]
                    if value > 0:  # Nur wenn Anteil > 0
                        rows.append((ct, version_label, value))

            if not rows:
                continue

            # --- Nodes ---
            unique_versions = sorted(df_sub['Version 2'].unique(), key=int)
            version_nodes = [f"v{v}" for v in unique_versions]
            nodes = change_types + version_nodes
            node_indices = {node: i for i, node in enumerate(nodes)}

            # --- Links ---
            source = [node_indices[src] for src, _, _ in rows]
            target = [node_indices[tgt] for _, tgt, _ in rows]
            value = [val for _, _, val in rows]

            colors_links = [shade_color(COLORS.get(src, "#888888"), 0.7) for src, _, _ in rows]
            colors_nodes = [COLORS.get(ct, "#888888") for ct in change_types] + ["orange"] * len(version_nodes)

            # --- Layout ---
            def distribute_y(n, offset=0.0, scale=1.0, gap=0.05):
                """Verteilt n Balken gleichmäßig mit optionalem Zwischenabstand (gap)."""
                if n == 1:
                    return [offset + scale / 2.0]
                total_gap = gap * (n - 1)
                step = (scale - total_gap) / (n - 1) if n > 1 else 0
                return [offset + i * (step + gap) for i in range(n)]

            node_x = [0.0] * len(change_types) + [1.0] * len(version_nodes)

            # Change Types links (0–1)
            node_y = distribute_y(len(change_types), offset=0.0, scale=1.0, gap=0.0)

            # Versions rechts (z. B. 0.05–0.95 mit Lücken)
            node_y_versions = distribute_y(len(version_nodes), offset=0.05, scale=0.95, gap=0.00)

            node_y = node_y + node_y_versions

            # --- Sankey ---
            fig = go.Figure(data=[go.Sankey(
                node=dict(
                    pad=40,
                    thickness=15,
                    line=dict(color="black", width=0.5),
                    label=nodes,
                    color=colors_nodes,
                    x=node_x,
                    y=node_y
                ),
                link=dict(
                    source=source,
                    target=target,
                    value=value,
                    color=colors_links
                )
            )])

            fig.update_layout(
                title_text="", #f"Variant: {variant}, Prompt: {prompt}",
                font_size=12,
                width=1000,
                height=250,
                margin=dict(l=0, r=0, t=0, b=0),  # reduziert weißen Rahmen
                overwrite=True
            )

            # --- Export ---
            prompt_subdir = os.path.join(config.paths.PLOTS_SUBDIR, "prompts", str(prompt), "horizontal_plots", "sankey")
            prompt_dir = os.path.join(output_dir, prompt_subdir)
            os.makedirs(prompt_dir, exist_ok=True)

            filename = f"{variant}_{prompt}_sankey.{format}"
            fig.write_image(os.path.join(prompt_dir, filename), scale=2)
            print(f"Saved: {filename}")



def histplot_plot(df, output_dir):
    """
    Erstellt Diagramme aus den bereits normalisierten Prozentwerten pro Change-Type.
    Erwartet Spalten wie: Variant, Prompt, Version 1, Version 2, Rename, SyntaxOnly, ...
    """
    df = df.copy()
    variants = df[config.fields.variant].unique()

    # Relevante Change-Type Spalten (alle, die in config.MODIFICATIONS_COLS stehen)
    change_types = [ct for ct in config.fields.MODIFICATIONS_COLS if ct in df.columns]
    if not change_types:
        print("No change-type columns found for histplot.")
        return

    version_col = config.fields.version2 if config.fields.version2 in df.columns else "Version 2"

    for variant in variants:
        df_var = df[df[config.fields.variant] == variant]
        prompts = df_var[config.fields.prompt].unique()

        for prompt in prompts:
            df_sub = df_var[df_var[config.fields.prompt] == prompt].copy()
            if df_sub.empty:
                continue

            df_sub[version_col] = pd.to_numeric(df_sub[version_col], errors='coerce')
            df_sub = df_sub.dropna(subset=[version_col])
            if df_sub.empty:
                continue

            # Aggregate by iteration/version
            agg = df_sub.groupby(version_col)[change_types].mean().reset_index()
            if agg.empty:
                continue

            # Long format for histogram
            long_df = agg.melt(
                id_vars=[version_col],
                value_vars=change_types,
                var_name="Change Type",
                value_name="Percentage"
            )

            # Convert to percentage values if still in [0,1]
            if long_df["Percentage"].between(0, 1).all():
                long_df["Percentage"] = long_df["Percentage"] * 100

            versions = sorted(agg[version_col].dropna().unique())
            x_labels = [f"v{int(v)}" for v in versions]

            plt.figure(figsize=(6, 3))
            ax = sns.histplot(
                data=long_df,
                x=version_col,
                weights="Percentage",
                hue="Change Type",
                hue_order=change_types,
                palette=[COLORS.get(ct, "#888888") for ct in change_types],
                multiple="dodge",
                discrete=True,
                stat="percent",
                shrink=0.8
            )

            plt.xlabel(config.fields.version, fontsize=12)
            plt.ylabel("Percentage (%)", fontsize=12)
            plt.ylim(0, 40) # hardcoded, but ensures consistent y-axis across all plots for better comparability
            plt.xticks(versions, x_labels, fontsize=12)
            plt.gca().spines['top'].set_visible(False)
            plt.gca().spines['right'].set_visible(False)

            sns.move_legend(
                ax, 'upper right', ncol=3, frameon=False, fontsize=9
            )

            plt.tight_layout()

            # --- Export ---
            prompt_subdir = os.path.join(config.paths.PLOTS_SUBDIR, "prompts", str(prompt), "horizontal_plots", "histplot")
            prompt_dir = os.path.join(output_dir, prompt_subdir)
            os.makedirs(prompt_dir, exist_ok=True)

            filename = f"{variant}_{prompt}_histplot.{format}"
            plt.savefig(os.path.join(prompt_dir, filename), bbox_inches="tight")
            plt.close()
            print(f"Saved: {filename}")


def sankey_plot_detailed(df, output_dir):
    """Makes detailed sankey plot from OnlyDirectSuccessors"""

    df = df.copy()
    N = 10  # Top-N AST Types pro Change Type
    variants = df['Variant'].unique()

    # Neue Spalte für die aggregierte Classification
    df["Classification_agg"] = df["Classification"].apply(
        lambda x: config.fields.semantic_change if x in config.fields.SEMANTIC_CHANGE_COLS else x
    )

    df = df[~df["Classification_agg"].isin([config.fields.identical_partial_ast, config.fields.value_error])]

    # Nach aggregierter Spalte summieren
    df = (
        df.groupby(["Variant", "Prompt", "Classification_agg", "AST Type Combination", "Version 2"])
        ["Count"].sum()
        .reset_index()
    )

    for variant in variants:
        df_var = df[df['Variant'] == variant]
        prompts = df_var['Prompt'].unique()

        for prompt in prompts:
            df_sub = df_var[df_var['Prompt'] == prompt]

            # Top-N AST Types pro Change Type
            top_ast_per_class = (
                df_sub.groupby(['Classification_agg', 'AST Type Combination'])['Count']
                .sum()
                .groupby(level=0, group_keys=False)
                .nlargest(N)
                .reset_index()
            )
            top_ast_list = top_ast_per_class['AST Type Combination'].unique().tolist()
            df_sub_top = df_sub[df_sub['AST Type Combination'].isin(top_ast_list)]

            # Aggregation: Count pro Change Type → AST Type → Version
            agg = df_sub_top.groupby(['Classification_agg', 'AST Type Combination', 'Version 2'])[
                'Count'].sum().reset_index()

            # Nodes erstellen
            change_types = list(agg['Classification_agg'].unique())
            ast_types = list(agg['AST Type Combination'].unique())
            versions = sorted(list(map(str, agg['Version 2'].unique())), key=int)

            nodes = change_types + ast_types + versions
            node_indices = {node: i for i, node in enumerate(nodes)}

            # Links erstellen
            source1 = [node_indices[row['Classification_agg']] for _, row in agg.iterrows()]
            target1 = [node_indices[row['AST Type Combination']] for _, row in agg.iterrows()]
            value1 = agg['Count'].tolist()
            color1 = [shade_color(COLORS.get(row['Classification_agg'], "#888888"), 0.8) for _, row in agg.iterrows()]

            source2 = [node_indices[row['AST Type Combination']] for _, row in agg.iterrows()]
            target2 = [node_indices[str(row['Version 2'])] for _, row in agg.iterrows()]
            value2 = agg['Count'].tolist()
            color2 = [shade_color(COLORS.get(row['Classification_agg'], "#888888"), 0.6) for _, row in agg.iterrows()]

            links = {
                'source': source1 + source2,
                'target': target1 + target2,
                'value': value1 + value2,
                'color': color1 + color2
            }

            # Farben für Ebenen
            colors = [COLORS.get(ct, "#888888") for ct in change_types] + ["lightgreen"] * len(ast_types) + ["orange"] * len(versions)

            # --- x/y-Positionen für Sankey-Knoten ---
            n_change = len(change_types)
            n_ast = len(ast_types)
            n_versions = len(versions)

            # x-Koordinaten: links → Mitte → rechts
            node_x = [0.0] * n_change + [0.5] * n_ast + [1.0] * n_versions

            # y-Koordinaten gleichmäßig verteilt pro Ebene
            def distribute_y(n, offset=0.0, scale=1.0):
                return [offset + scale * (i / (n - 1) if n > 1 else 0.5) for i in range(n)]

            # Change Types links (0 bis 1)
            node_y = distribute_y(len(change_types), offset=0.0, scale=1.0)

            # Versions rechts (0 bis 1), aber leicht nach unten verschoben (z.B. 0.1 bis 0.9)
            node_y_versions = distribute_y(n_change) + distribute_y(n_ast) + distribute_y(n_versions)
            node_y = node_y + node_y_versions


            # Sankey Plot erstellen
            fig = go.Figure(data=[go.Sankey(
                node=dict(
                    pad=30,
                    thickness=15,
                    line=dict(color="black", width=0.5),
                    label=nodes,
                    color=colors,
                    x=node_x,
                    y=node_y
                ),
                link=links,
                hoverlabel=dict(bgcolor="white", font_size=12)
            )])

            fig.update_layout(
                title_text=f"Variant: {variant}, Prompt: {prompt} (Top-{N} AST Types)",
                font_size=12,
                width=1200,
                height=700
            )

            # Prompt-spezifisches Verzeichnis
            prompt_subdir = os.path.join(config.paths.PLOTS_SUBDIR, "prompts", str(prompt), "sankey")
            prompt_dir = os.path.join(output_dir, prompt_subdir)
            os.makedirs(prompt_dir, exist_ok=True)

            # Export als PNG
            filename = f"{variant}_{prompt}_sankey_detailed.{format}"
            fig.write_image(os.path.join(prompt_dir, filename), width=4000, height=2500, scale=2)
            print(f"Saved: {filename}")

def sunburst_plots(df, output_dir):

    # Zeilen mit config.no_prompt ausfiltern
    df_complete = df[df[config.fields.prompt] != config.fields.no_prompt]

    fig_complete = px.sunburst(
        df_complete,
        path=[config.fields.prompt, config.fields.classification, config.fields.ast_type_combination],
        values=config.fields.count,
        color=config.fields.classification,
    )

    # Titel obendrüber und Layout anpassen
    fig_complete.update_layout(
        #title=dict(
        #    text="Sunburst: Prompt → Change Type → AST Node Type",
        #    x=0.5,                # zentriert
        #    xanchor="center",
        #    yanchor="top"
        #),
        width=1000,  # quadratisch
        height=1000
    )

    # Save sunburst in a global instead of prompt-specific subdir
    plots_dir = os.path.join(output_dir, config.paths.PLOTS_SUBDIR, "global")
    os.makedirs(plots_dir, exist_ok=True)

    # Export als PNG
    filename = os.path.join(plots_dir, f"sunburst_diagram_complete.{format}")
    fig_complete.write_image(filename,  width=1500, height=800, scale=4)
    print(f"Saved: {filename}")


    # Filter: keine no_prompt-Prompts und nur die SemanticChangeCols
    df_semantic_ast = df[(df[config.fields.prompt] != config.fields.no_prompt) & (df[config.fields.classification].isin(
        config.fields.SEMANTIC_CHANGE_COLS))]


    fig_semantic = px.sunburst(
        df_semantic_ast,
        path=[config.fields.prompt, config.fields.classification],
        values=config.fields.count,
        color=config.fields.classification
    )

    fig_semantic_ast = px.sunburst(
        df_semantic_ast,
        path=[config.fields.prompt, config.fields.classification, config.fields.ast_type_combination],
        values=config.fields.count,
        color=config.fields.classification
    )


    # Titel obendrüber und Layout anpassen
    fig_semantic.update_layout(
        title=dict(
            text="Sunburst: Prompt → Change Type",
            x=0.5,
            xanchor="center",
            yanchor="top"
        ),
        width=1000,
        height=1000
    )

    fig_semantic_ast.update_layout(
        title=dict(
            text="Sunburst: Prompt → Change Type → AST Node Type",
            x=0.5,
            xanchor="center",
            yanchor="top"
        ),
        width=1000,
        height=1000
    )

    # Export als PNG
    filename_semantic_ast = os.path.join(plots_dir, f"sunburst_diagram_semantic-ast.{format}")
    fig_semantic_ast.write_image(filename_semantic_ast, width=1000, height=1000, scale=4)
    print(f"Saved: {filename_semantic_ast}")

    filename_semantic = os.path.join(plots_dir, f"sunburst_diagram_only-semantic.{format}")
    fig_semantic.write_image(filename_semantic, width=1000, height=1000, scale=4)
    print(f"Saved: {filename_semantic}")

# ##############################################################################################
# MORE ADVANCED STUFF
# ##############################################################################################

# ---------- 1) Scatter + density ----------
def analyze_similarity_vs_percent_modified(df, output_dir, prompt_specific=False):

    percent_mod_col = config.fields.modifications

    if prompt_specific:
        for prompt, gdf in df.groupby(config.fields.prompt):

            if prompt == config.fields.no_prompt:
                continue

            # Prompt-specific subdir
            prompt_subdir = os.path.join(config.paths.PLOTS_SUBDIR, "prompts", str(prompt))
            prompt_dir = os.path.join(output_dir, prompt_subdir)
            os.makedirs(prompt_dir, exist_ok=True)

            plt.figure(figsize=(8,6))
            hb = plt.hexbin(gdf[percent_mod_col], gdf[config.fields.avg_similarity_score],
                            gridsize=40, cmap='viridis', mincnt=1)
            plt.colorbar(hb, label='counts')
            if len(gdf) > 30:
                sns.regplot(x=percent_mod_col, y=config.fields.avg_similarity_score,
                            data=gdf.sample(min(len(gdf), 2000), random_state=42),
                            scatter=False, lowess=True, color='red', line_kws={'lw':1.2})

            # --- Korrelationskoeffizient r ---
            if gdf[percent_mod_col].nunique() > 1:
                r = gdf[percent_mod_col].corr(gdf[config.fields.avg_similarity_score])
                plt.text(0.8, 0.95, f"r = {r:.2f}",
                         transform=plt.gca().transAxes, fontsize=11,
                         verticalalignment='top', bbox=dict(boxstyle="round", fc="w"))

            plt.xlabel(percent_mod_col)
            plt.ylabel(config.fields.avg_similarity_score)
            plt.title(f'Avg Similarity vs %Modified — {config.fields.prompt}={prompt}')
            plt.tight_layout()
            fname = f"scatter_similarity_vs_percent_modified_{config.fields.prompt}_{prompt}.{format}"
            plt.savefig(os.path.join(prompt_dir, fname), dpi=200)
            plt.close()
            print(
                f"({prompt}) Saved {fname} in ‘{prompt_dir}’.")


    else:

        # Global subdir
        global_subdir = os.path.join(output_dir, config.paths.PLOTS_SUBDIR, "global")
        os.makedirs(global_subdir, exist_ok=True)

        plt.figure(figsize=(8,6))
        hb = plt.hexbin(df[percent_mod_col], df[config.fields.avg_similarity_score],
                        gridsize=60, cmap='viridis', mincnt=1)
        plt.colorbar(hb, label='counts')
        sns.regplot(x=percent_mod_col, y=config.fields.avg_similarity_score,
                    data=df.sample(min(len(df), 20000), random_state=42),
                    scatter=False, lowess=True, color='red', line_kws={'lw':1.5})

        # --- Korrelationskoeffizient r ---
        if df[percent_mod_col].nunique() > 1:
            r = df[percent_mod_col].corr(df[config.fields.avg_similarity_score])
            plt.text(0.8, 0.95, f"r = {r:.2f}",
                     transform=plt.gca().transAxes, fontsize=11,
                     verticalalignment='top', bbox=dict(boxstyle="round", fc="w"))

        plt.xlabel(percent_mod_col)
        plt.ylabel(config.fields.avg_similarity_score)
        plt.title('Avg Similarity vs %Modified (global)')
        plt.tight_layout()
        plt.savefig(os.path.join(global_subdir, f"scatter_similarity_vs_percent_modified.{format}"), dpi=200)
        plt.close()

        print(
            f"(scatter_similarity_vs_percent_modified) Saved plots in ‘{global_subdir}’.")


# ---------- 2) Boxplots ----------
def analyze_similarity_by_change_types(df, output_dir, prompt_specific=False):

    change_cols = sorted([c for c in config.fields.MODIFICATIONS_COLS if c not in [config.fields.other]]) # + config.SEMANTIC_CHANGE_COLS

    if prompt_specific:
        for prompt, gdf in df.groupby(config.fields.prompt):

            if prompt == config.fields.no_prompt:
                continue

            # Prompt-specific subdir
            prompt_subdir = os.path.join(config.paths.PLOTS_SUBDIR, "prompts", str(prompt))
            prompt_dir = os.path.join(output_dir, prompt_subdir)
            os.makedirs(prompt_dir, exist_ok=True)

            box_df = []
            for c in change_cols:
                tmp = gdf[[config.fields.avg_similarity_score, c]].copy()
                tmp['Present'] = (tmp[c].fillna(0) > 0).astype(int)
                tmp['ChangeType'] = c
                box_df.append(tmp[[config.fields.avg_similarity_score, 'Present', 'ChangeType']])
            if not box_df:
                continue
            box_df = pd.concat(box_df, ignore_index=True)

            if len(box_df) > 30:
                plt.figure(figsize=(12,6))
                ax = sns.boxplot(x='ChangeType', y='Avg Similarity Score', hue='Present',
                                 data=box_df.sample(min(len(box_df), 3000), random_state=42), order=change_cols)

                # Grab handles and labels
                handles, labels = ax.get_legend_handles_labels()

                # Replace 0/1 with No/Yes
                new_labels = ["No" if l == "0" else "Yes" for l in labels]

                # Move legend below plot
                ax.legend(handles, new_labels, title="Change Type Present", bbox_to_anchor=(0.5, -0.15),
                          loc="upper center", ncol=2, fontsize=12)

                # More distance of labels to plot
                ax.set_xlabel("x = Change Type", labelpad=10, fontsize=12)
                ax.set_ylabel("y = Avg Similarity Score", labelpad=10, fontsize=12)

                # Collect correlations per change type
                corr_texts = []
                for c in change_cols:
                    subset = gdf[['Avg Similarity Score', c]].dropna()
                    if subset[c].nunique() > 1:
                        r = subset[c].corr(subset['Avg Similarity Score'])
                        corr_texts.append(f"{c}: r={r:.2f}")

                # Add correlation legend-like text below plot
                if corr_texts:
                    corr_str = " | ".join(corr_texts)
                    plt.figtext(0.5, -0.05, corr_str, wrap=True, ha="center", fontsize=11)

                plt.title(f'Similarity distributions by change type — {config.fields.prompt}={prompt}')
                plt.tight_layout()
                fname = f"boxplot_similarity_by_change_type_{config.fields.prompt}_{prompt}.{format}"
                plt.savefig(os.path.join(prompt_dir, fname), dpi=200, bbox_inches="tight")
                plt.close()

                print(
                    f"({prompt}) Saved {fname} in ‘{prompt_dir}’.")

    else:
        # Global subdir
        global_subdir = os.path.join(output_dir, config.paths.PLOTS_SUBDIR, "global")
        os.makedirs(global_subdir, exist_ok=True)

        box_df = []
        for c in change_cols:
            tmp = df[[config.fields.avg_similarity_score, c]].copy()
            tmp['Present'] = (tmp[c].fillna(0) > 0).astype(int)
            tmp['ChangeType'] = c
            box_df.append(tmp[[config.fields.avg_similarity_score, 'Present', 'ChangeType']])
        box_df = pd.concat(box_df, ignore_index=True)

        plt.figure(figsize=(12,6))
        ax = sns.boxplot(x='ChangeType', y='Avg Similarity Score', hue='Present',
                         data=box_df.sample(min(len(box_df), 8000), random_state=42), order=change_cols)

        # Grab handles and labels
        handles, labels = ax.get_legend_handles_labels()

        # Replace 0/1 with No/Yes
        new_labels = ["No" if l == "0" else "Yes" for l in labels]

        # Move legend below plot
        ax.legend(handles, new_labels, title="Change Type Present", bbox_to_anchor=(0.5, -0.15), loc="upper center", ncol=2, fontsize=12)

        # More distance of labels to plot
        ax.set_xlabel("x = Change Type", labelpad=10, fontsize=12)
        ax.set_ylabel("y = Avg Similarity Score", labelpad=10, fontsize=12)

        # Collect correlations per change type
        corr_texts = []
        for c in change_cols:
            subset = df[['Avg Similarity Score', c]].dropna()
            if subset[c].nunique() > 1:
                r = subset[c].corr(subset['Avg Similarity Score'])
                corr_texts.append(f"{c}: r={r:.2f}")

        # Add correlation legend-like text below plot
        if corr_texts:
            corr_str = " | ".join(corr_texts)
            plt.figtext(0.5, -0.05, corr_str, wrap=True, ha="center", fontsize=11)

        plt.title('Similarity distributions by change type (global)')
        plt.tight_layout()
        plt.savefig(os.path.join(global_subdir, f"boxplot_similarity_by_change_type.{format}"),
                    dpi=200, bbox_inches="tight")
        plt.close()

        print(
            f"(analyze_similarity_by_change_types) Saved plots in ‘{global_subdir}’.")


# ---------- 3) Correlation heatmap ----------
def analyze_change_cooccurrence(df, output_dir, change_cols, prompt_specific=False, top_n=15):
    """
    Create correlation heatmaps of the top N change categories.
    Both global and (optionally) per-prompt versions.
    Additional annotations: mean absolute correlation and strongest pair.
    """

    if change_cols == config.fields.SEMANTIC_CHANGE_COLS:
        file_ext = "Semantic"

    else:
        file_ext = ""

    # Pick top_n most frequent change types
    change_sums = df[change_cols].fillna(0).sum().sort_values(ascending=False)
    top_changes = change_sums.head(top_n).index.tolist()

    def _annotate_corr_info(corr_mat):
        """Helper to compute and annotate summary statistics below the plot."""
        # Mask diagonal
        mask = ~np.eye(corr_mat.shape[0], dtype=bool)
        vals = corr_mat.values[mask]

        # Average absolute correlation
        mean_abs_corr = np.mean(np.abs(vals))

        # Strongest pair
        abs_corr = np.abs(corr_mat.values)
        np.fill_diagonal(abs_corr, 0)  # ignore diagonal
        max_idx = np.unravel_index(abs_corr.argmax(), abs_corr.shape)
        pair = (corr_mat.index[max_idx[0]], corr_mat.columns[max_idx[1]])
        max_val = corr_mat.values[max_idx]

        # Add text below plot
        text = (f"Mean |r| = {mean_abs_corr:.2f} | "
                f"Strongest pair: {pair[0]} ↔ {pair[1]} (r = {max_val:.2f})")
        plt.figtext(0.5, -0.05, text, ha="center", fontsize=10)

    if prompt_specific:
        for prompt, gdf in df.groupby(config.fields.prompt):

            if prompt == config.fields.no_prompt:
                continue

            # Prompt-specific subdir
            prompt_subdir = os.path.join(config.paths.PLOTS_SUBDIR, "prompts", str(prompt))
            prompt_dir = os.path.join(output_dir, prompt_subdir)
            os.makedirs(prompt_dir, exist_ok=True)

            if gdf[top_changes].sum().sum() == 0:
                continue  # skip if no data

            # Compute correlation matrix
            corr_mat = gdf[top_changes].fillna(0).astype(float).corr()

            plt.figure(figsize=(11,9))
            sns.heatmap(corr_mat, annot=True, fmt=".2f", cmap='coolwarm',
                        vmin=-1, vmax=1, cbar_kws={"shrink": .8})

            # Title and labels
            plt.title(f'{file_ext} Change co-occurrence correlation — {config.fields.prompt}={prompt}',
                      pad=20)
            plt.xticks(rotation=45, ha="right")
            plt.yticks(rotation=0)

            # Annotate summary info
            _annotate_corr_info(corr_mat)

            plt.tight_layout()
            fname = f"change_cooccurrence_{file_ext}_corr_{config.fields.prompt}_{prompt}.{format}"
            plt.savefig(os.path.join(prompt_dir, fname), dpi=200, bbox_inches="tight")
            plt.close()

            print(
                f"({prompt}) Saved {fname} in ‘{prompt_dir}’.")


    else:
        # Global subdir
        global_subdir = os.path.join(output_dir, config.paths.PLOTS_SUBDIR, "global")
        os.makedirs(global_subdir, exist_ok=True)

        # Compute correlation matrix
        corr_mat = df[top_changes].fillna(0).astype(float).corr()

        plt.figure(figsize=(11,9))
        sns.heatmap(corr_mat, annot=True, fmt=".2f", cmap='coolwarm',
                    vmin=-1, vmax=1, cbar_kws={"shrink": .8})

        # Title and labels
        plt.title(f'{file_ext} Change co-occurrence correlation',
                  pad=20)
        plt.xticks(rotation=45, ha="right")
        plt.yticks(rotation=0)

        # Annotate summary info
        _annotate_corr_info(corr_mat)

        plt.tight_layout()
        plt.savefig(os.path.join(global_subdir, f"change_cooccurrence_{file_ext}_corr.{format}"),
                    dpi=200, bbox_inches="tight")
        plt.close()

        print(
            f"(analyze_change_occurence) Saved plots in ‘{global_subdir}’.")




def plot_rq3_data(output_dir, chosen_change_types):

    """output_dir = results_dir of Evaluator"""

    global_subdir = os.path.join(output_dir, config.paths.PLOTS_SUBDIR, "statistical_analysis")
    os.makedirs(global_subdir, exist_ok=True)

    # CSV laden (die "mean±std"-Datei)
    df = pd.read_csv(os.path.join(output_dir, "analysis", "statistical_analysis", "rq3_desc.csv"))

    # Falls die Spalten noch als "x ± y" Strings gespeichert sind:
    # in Mittelwert und Std trennen
    for col in df.columns[2:]:  # ab der 3. Spalte (Rename, SyntaxOnly ...)
        df[[f"{col}_mean", f"{col}_std"]] = df[col].str.split(" ± ", expand=True).astype(float)

    # Balkendiagramm
    for change_type in chosen_change_types:
        fig, ax = plt.subplots(figsize=(8, 5))
        x = range(len(df))
        ax.bar(x, df[f"{change_type}_mean"], yerr=df[f"{change_type}_std"], capsize=5, color="skyblue")
        ax.set_xticks(x)
        ax.set_xticklabels(df[["Variant", "Prompt"]].apply(lambda row: f"{row.iloc[0]}-{row.iloc[1]}", axis=1), rotation=45,
                           ha="right")
        ax.set_ylabel(f"(mean ± std)")
        ax.set_title(f"Change Type: {change_type}")

        plt.tight_layout()


        plt.savefig(os.path.join(global_subdir, f"barplot_mean_std_{change_type}.{format}"),
                    dpi=200, bbox_inches="tight")
        plt.close()

    print(f"Saved rq3 plots in ‘{global_subdir}’.")


# ##############################################################################################

def main():
    pass


if __name__ == "__main__":
    print("Running VisualizeAnalysis.py ...")
    main()
