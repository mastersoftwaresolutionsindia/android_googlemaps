android_googlemaps
==================

Demo Integration of Google Maps v2 in Android 

**Introduction**
------------

 This is a demo Application in Android showing the integration of Google Maps V2. 
 
 

**How to Use**
----------
Assuming you’re using the Eclipse Development Environment with the ADT plugin. To run this demo application in you have to Import the project From 

 - **File > Import > Android > Existing Android Code Into Workspace >**
 - **Browse.. >**
 - **Navigate to the path where you have cloned the project**
 - click **OK**

After importing the project if any Error or Exclamation is showing on the project folder then you have to Import one Library Project from your Android's SDK Folder. To do that click on: 

 - **File > Import > Android > Existing Android Code Into Workspace >**
 - **Browse.. >**
 - **(path to your SDK folder)/extras/google/google_play_services/libproject**
 - **select google-play-services_lib**
 - click **OK**

Now right click on GoogleMap project and select Properties and on the Left Panel of the window select Android, on the right panel under Is Library section click Add and select google-play-services_lib folder and click OK and in previous window again click Apply and OK. Now the must me error free.

**Setting up Google Maps V2 API KEY**
At the bottom of the AndroidManifest.xml file their will be a meta-data tag for setting the API KEY. Paste your API KEY in android:name attribute

**Generating Google Maps V2 API KEY**

If you don't have any API KEY then you have to generate one in-order to make the demo project work. 

 - Sign-Up [Google API Console][1] and create a new project.
 - Select **Services** at the left column under your project name
 - Enable **Google Cloud Messaging for Android**
 - After enabling **GCM** select **API Access** and search for **Key for browser apps (with referers)** and copy the API key and paste it into AndroidManifest.xml file as reffered to the above section.
 - If you are unable to find **Key for browser apps (with referers)** under **API Access** then you have to create a new Browser key.
 - Click **Create new Browser Key..** button at the bottom of the page and click create button on the new dialog without filling any detail, this will create a new Browser key for **Any referer allowed**
 - Now copy and paste the API Key in AndroidManifest.xml file

Clean your project and test it on a real device as you may face some crash on emulator because of Open GLES V2.
 
  [1]: https://code.google.com/apis/console/
