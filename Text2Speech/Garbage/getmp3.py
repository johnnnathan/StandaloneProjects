import os
import requests
from selenium import webdriver
from selenium.webdriver.firefox.service import Service
from selenium.webdriver.common.by import By
from selenium.webdriver.firefox.options import Options
from urllib.parse import urljoin

# Path to the GeckoDriver
gecko_service = Service('/usr/local/bin/geckodriver')  # Adjust this if necessary

# Folder to save downloaded MP3 files
download_folder = "./mp3_downloads"

# Create folder if it doesn't exist
if not os.path.exists(download_folder):
    os.makedirs(download_folder)

# Initialize Firefox WebDriver
options = Options()
options.headless = True  # Run Firefox in headless mode (without opening a window)
driver = webdriver.Firefox(service=gecko_service, options=options)

def download_mp3_files(url):
    try:
        # Navigate to the URL
        driver.get(url)

        # Find all 'a' tags with href attributes ending in '.mp3'
        mp3_links = driver.find_elements(By.XPATH, "//a[contains(@href, '.mp3')]")
        
        if not mp3_links:
            print("No MP3 files found on the webpage.")
            return
        
        print(f"Found {len(mp3_links)} MP3 files.")
        
        for link in mp3_links:
            mp3_url = link.get_attribute('href')
            if mp3_url:
                print(f"Downloading: {mp3_url}")
                # Get the MP3 file name
                mp3_file_name = os.path.join(download_folder, mp3_url.split('/')[-1])

                # Download the MP3 file
                try:
                    response = requests.get(mp3_url, stream=True)
                    with open(mp3_file_name, 'wb') as f:
                        for chunk in response.iter_content(chunk_size=8192):
                            if chunk:
                                f.write(chunk)
                    print(f"Saved: {mp3_file_name}")
                except Exception as e:
                    print(f"Error downloading {mp3_url}: {e}")

    finally:
        # Quit the driver
        driver.quit()

if __name__ == "__main__":
    website_url = input("Enter the website URL: ")
    download_mp3_files(website_url)
