# MyDiaries — AI-Powered Mood Tracking Diary App (NLP + Computer Vision)

MyDiaries is an Android diary application enhanced with AI that analyzes users’ daily journal entries and optional images to detect emotional states such as **sadness, joy, love, anger, and fear**. Based on the predicted mood, the app provides supportive messages and mood-based quotes, and visualizes emotional trends over time through a mood tracking chart.

This project was motivated by the fact that mental health support is not always accessible and can be stigmatized in many communities. While journaling is a common way to express emotions, most diary apps remain passive and do not help users reflect on their emotional patterns. HeartLog transforms journaling into a more interactive experience by offering emotional insights and simple encouragement based on the user’s mood.

---
## Demo
<img width="239" height="502" alt="image" src="https://github.com/user-attachments/assets/41176abb-3b9c-4a16-b618-0edb0e1cae18" />


---


## Links
- **Image emotion model (Colab):** https://colab.research.google.com/drive/1hd1Gl-6BsMMjuG1xGzaxJ_Rd3MlHgaTH?usp=sharing
- **Text sentiment model (Colab):** https://colab.research.google.com/drive/1iWO4gWPXtIHf5vstkREnczHrU9ZauSh9?usp=sharing

---

## Key Features
- Write daily diary entries and save them securely
- Attach optional images from your day
- AI-based mood detection from:
  - **Text (NLP emotion classification)**
  - **Image (CNN facial emotion recognition)**
- Supportive messages and mood-based quotes
- Mood tracking chart to visualize emotional trends over time

---

## Mood Labels
The app predicts emotions such as:
- Sadness (0)
- Joy (1)
- Love (2)
- Anger (3)
- Fear (4)

---

## Tech Stack
### Mobile App
- Android Studio
- Java
- Firebase(database)

### AI Models
- **Text emotion model:** BiLSTM (NLP)
- **Image emotion model:** CNN (Computer Vision)

### Backend
- Flask API (serves both models and returns predictions)

---

## Datasets Used
- **FER-2013 (Facial Expression Recognition):**  
  https://www.kaggle.com/datasets/msambare/fer2013

- **Emotion Dataset for NLP (tweet-based):**  
  https://www.kaggle.com/datasets/parulpandey/emotion-dataset/data

---

## How It Works (Simple Explanation)
1. The user writes a diary entry and optionally attaches an image.
2. The app sends the text/image to a Flask API.
3. The AI models predict the user’s emotion.
4. The app displays the mood result with supportive messages and quotes.
5. The mood is saved and visualized over time through a tracking chart.

---

## Project Structure (Suggested)
```bash
.
├── android-app/          # Android project (Java)
├── backend/              # Flask API
├── models/               # Trained models (CNN + BiLSTM)
├── notebooks/            # Colab / training notebooks
├── screenshots/          # App screenshots
└── README.md

---
## Author
Ikram Bichbich
