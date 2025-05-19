# IrrigationAlert App

> Real-time alert system for smart rice paddy field monitoring — part of a government-funded agricultural automation project.

##  Overview

**IrrigationAlert** is an Android application developed as part of a government-funded project aimed at revolutionizing agricultural field monitoring through automation and real-time communication.

It works alongside a rover deployed in rice paddy fields that autonomously patrols the area and detects potential issues such as **low irrigation**, **crop stress**, or **environmental anomalies**. When an issue is detected, the rover triggers an alert function that immediately sends a **push notification** with an **alert sound** to this mobile app, enabling farmers to take timely action.

##  Features

- 🔔 **Instant Alert Notifications**: Get real-time push notifications from the rover when issues are detected in the field along with time the alert was generated.
- 🎵 **Audio Alert**: Each notification is accompanied by an audible alert to ensure it is noticed quickly.
- 🤖 **Rover Integration**: Designed to work seamlessly with a Raspberry Pi-powered rover that analyzes field conditions.
- 📲 **Minimal UI, Maximum Impact**: Lightweight and focused interface for fast and easy alert handling.
- 🔐 **Firebase Backend**: Powered by Firebase Cloud Messaging (FCM) for reliable and secure data delivery.

##  Project Background

This app was developed as part of a **government-funded agricultural automation project**. The goal is to modernize traditional farming methods using **autonomous rovers and IoT technologies** to reduce manual effort and respond quickly to field anomalies.

The rover traverses rice fields, collecting sensor data and detecting issues. Upon identifying a problem (like low irrigation levels), it triggers this Android app to notify the farmer immediately.

## 🛠 Tech Stack

- **Android** (Java)
- **Firebase Cloud Messaging**
- **Raspberry Pi Rover** (for backend alerting – external to this repo)
- **Gradle** build system

##  App Structure

```
IrrigationAlert/
└── app/
    ├── src/main/
    │   ├── java/com/example/irrigationalert/
    │   │   ├── MainActivity.java
    │   │   └── MyFirebaseMessagingService.java
    │   └── res/
    │       └── drawable, layout, values, etc.
    ├── AndroidManifest.xml
    └── release/
        └── app-release.apk
```

##  How to Use

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/IrrigationAlert.git
   cd IrrigationAlert/IrrigationAlert
   ```

2. **Open in Android Studio**.

3. **Set up Firebase**:
   - Add your `google-services.json` to `app/`.
   -  Add your `google-services.json` to `alert1/` and `alert2/` files also.

4. **Connect device and run the app**.

5. **Deploy rover** in the field and wait for alerts!

6. **You can directly install the app from "install apk" folder** 

## 🛰 Real-World Impact

✅ Saves time and manual effort in inspecting large fields  
✅ Provides timely updates that help prevent crop loss  
✅ Promotes digital adoption among farmers  

## 🎯 Future Improvements

- Display sensor data (e.g., temperature, moisture) alongside alerts  
- Add alert history and visualization dashboard
- Expand to other crop types and terrains  

## 👤 Developed By

- **Faiz Ali** – Android Developer & Project Contributor  
  *(In collaboration with Indian Institute of Science Education and Research(IISER), Thiruvananthpuram)*  


# App Home Screen And Notification Screen Image
<p float="left">
  <img src="https://github.com/user-attachments/assets/37edca07-3d7b-443f-8f8e-bba9dba56ee9" width="45%" />
  <img src="https://github.com/user-attachments/assets/af1785a4-2672-4dba-ab85-33c14cc80ff6" width="45%" />
</p>


## 📃 License

Licensed under the [MIT License](LICENSE).

---

> 💡 This app is part of a broader mission to empower farmers through technology. Star 🌟 this repo if you'd like to support smart agriculture!
