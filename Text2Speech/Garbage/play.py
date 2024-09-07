import pyttsx3

def sound_out_text(text):
    # Initialize the text-to-speech engine
    engine = pyttsx3.init()
    # Set properties (optional)
    engine.setProperty('rate', 150)  # Speed of speech
    engine.setProperty('volume', 1)  # Volume (0.0 to 1.0)
    # Speak the text
    engine.say(text)
    engine.runAndWait()

def process_text(text):
    # Directly sound out the text
    print(f"Processing text: {text}")
    sound_out_text(text)

# Example usage
if __name__ == "__main__":
    text = input("Enter text to process: ")
    process_text(text)
