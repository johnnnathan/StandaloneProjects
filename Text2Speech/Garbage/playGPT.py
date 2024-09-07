from pydub import AudioSegment
from pydub.playback import play
import os
import random
import threading
import time

# Path to the mp3 folder
mp3_folder = 'mp3/'

# Define the mapping from phonetic symbols to MP3 files
phonetic_to_mp3 = {
    'ai': 'ai.mp3',
    'j': 'j.mp3',
    'j-x57k1g': 'j-x57k1g.mp3',
    'sh': 'sh.mp3',
    'ash': 'ash.mp3',
    'k': 'k.mp3',
    'small_cap_i-p6cj9p': 'small_cap_i-p6cj9p.mp3',
    'b': 'b.mp3',
    'l': 'l.mp3',
    's': 's.mp3',
    'd': 'd.mp3',
    'm-guw48u': 'm-guw48u.mp3',
    'th': 'th.mp3',
    'e': 'e.mp3',
    'mid_central-gqxn5h': 'mid_central-gqxn5h.mp3',
    't': 't.mp3',
    'E': 'E.mp3',
    'n-1smc314': 'n-1smc314.mp3',
    'u': 'u.mp3',
    'eth-icm6j7': 'eth-icm6j7.mp3',
    'ng': 'ng.mp3',
    'v': 'v.mp3',
    'f': 'f.mp3',
    'oh': 'oh.mp3',
    'voiceless_affricate-1athyop': 'voiceless_affricate-1athyop.mp3',
    'g': 'g.mp3',
    'oi': 'oi.mp3',
    'w': 'w.mp3',
    'h': 'h.mp3',
    'o': 'o.mp3',
    'x-1rkf9v4': 'x-1rkf9v4.mp3',
    'horseshoe-1inthj0': 'horseshoe-1inthj0.mp3',
    'ow': 'ow.mp3',
    'zh': 'zh.mp3',
    'i-gvz91l': 'i-gvz91l.mp3',
    'p': 'p.mp3',
    'z': 'z.mp3'
}

# Speed factor
speed_factor = 1.5

# Preload all sounds
sound_cache = {}
for symbol, filename in phonetic_to_mp3.items():
    filepath = os.path.join(mp3_folder, filename)
    if os.path.isfile(filepath):
        sound = AudioSegment.from_file(filepath)
        # Adjust speed
        sound = sound._spawn(sound.raw_data, overrides={
            "frame_rate": int(sound.frame_rate * speed_factor)
        })
        sound = sound.set_frame_rate(sound.frame_rate)
        sound_cache[symbol] = sound
    else:
        print(f"File {filename} not found!")

def play_sound(symbol):
    sound = sound_cache.get(symbol)
    if sound:
        play(sound)
    else:
        print(f"Sound for {symbol} not found!")

def remove_consecutive_duplicates(word):
    # Remove consecutive duplicate characters
    result = []
    prev_char = None
    for char in word:
        if char != prev_char:
            result.append(char)
        prev_char = char
    return ''.join(result)

def process_text(text):
    words = text.split()
    for word in words:
        # Remove consecutive duplicate characters
        cleaned_word = remove_consecutive_duplicates(word)
        
        if cleaned_word.startswith('e'):
            # Play the 'E.mp3' file for words starting with 'e'
            print(f"Playing sound for word starting with 'e': {cleaned_word}")
            play_sound('E')
            time.sleep(1)  # Adjust this sleep time if needed for better pacing
        else:
            # Loop over each character in the cleaned word
            for char in cleaned_word:
                # Check if there's a specific sound for the character or symbol
                if char in phonetic_to_mp3:
                    print(f"Playing sound for character: {char}")
                    play_sound(char)
                    time.sleep(1)  # Adjust this sleep time if needed for better pacing
                else:
                    # Play a fallback sound if the specific file is not found
                    print(f"Sound for {char} not found, playing fallback.")
                    # Play individual letter sound if available
                    if char.lower() in phonetic_to_mp3:
                        play_sound(char.lower())
                        time.sleep(1)  # Adjust this sleep time if needed for better pacing
                    else:
                        # Randomly choose between 'e.mp3' and 'E.mp3'
                        fallback_file = random.choice(['e', 'E'])
                        play_sound(fallback_file)
                        time.sleep(1)  # Adjust this sleep time if needed for better pacing

# Example usage
if __name__ == "__main__":
    text = input("Enter text to process: ")
    process_text(text)
