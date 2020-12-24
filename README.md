# SongSearch

Hello! This is my Technical Interview Application for the Android Developer position. 
The SongSearch Application is a very simple Search Application using the iTunes Search Api.
On open, the application will not render anything but there's a Search Icon in the toolbar
from there, you can type to make a query and the iTunes Api will give a result list of songs



On selecting a result, it will go the Detail Activity in which more information about the
selected track (result) will be rendered out



**Architectural Pattern that was used and an explanation why it was chosen.**



I choose the Model-View-ViewModel because originally I want to use the MVC architecture
But as I code the API Builder, I got confused and the files got messy. So I switch to
MVVC beacause it leans more to the maintainability and cleanliness of code.
I easily understood on how to apply it in API calls


**Third-party libraries used with explanation on why and how it was used within the app.**



Picasso - Easy Image Rendering - Most of the Images used



Koleton - Skeletal Loading Look since I want my loading screen to be more sleeker and simple - Recyclerview
