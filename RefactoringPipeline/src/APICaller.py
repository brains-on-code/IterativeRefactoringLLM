import os
import re
from openai import OpenAI

from config.utils import setup_logger

# Setup logging
logging, handler = setup_logger("APICaller")

class APICaller:

    """
    A wrapper class for interacting with the OpenAI API to perform code refactoring tasks.
    """

    def __init__(self):
        """
        Initializes the APICaller.

        Args:
            token_path (str): Optional path to the API key file.
            api_key (str | None): Optional API key directly provided (used in tests).
        """

        api_key = os.getenv("MY_OPENAI_API_KEY")

        if api_key is None:
            raise RuntimeError(
                "No API token found. Please set the environment variable MY_OPENAI_API_KEY."
            )

        self.client = OpenAI(api_key=api_key)

    def refactor_code(self, code, prompt):
        """
        Sends a code snippet to the OpenAI chat completion endpoint to receive a refactored version
        of the input code aimed at improved readability.

        Parameters:
            code (str): The source code string that needs to be refactored.
            prompt (str): The prompt with which the code should be refactored.

        Returns:
            str: The refactored code returned by the model, with any surrounding Markdown code block
            delimiters removed.
        """

        logging.info("Calling API...")
        logging.debug(f"{prompt}\n{code}")

        response = self.client.chat.completions.create(
            model="gpt-5.1", # gpt-5.1
            #service_tier="flex", # cheaper, but slower
            messages=[
                {"role": "system",
                 "content": "Return only the refactored code. No additional text or explanation."},
                {"role": "user", "content": f"{prompt}\n{code}"}
            ]
            , temperature=0.0  # ideally set to 0, but -nano does not support it (higher temperature value = more creative answers)
        )

        raw_code = response.choices[0].message.content.strip()

        # Since response is in Markdown format --> delete ```java ... ``` block around code
        match = re.search(r"```(?:java)?\n(.*?)\n```", raw_code, re.DOTALL)

        if match:
            return match.group(1).strip()

        return raw_code
