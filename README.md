# EasyWebservice 
Fast and easy RESTful api calls for android.
If using Retrofit is hard, or you are tired of handling json responses, this library is for you.

[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-EasyWebservice-green.svg?style=flat )]( https://android-arsenal.com/details/1/7117 )
[![](https://jitpack.io/v/ashkanpower/easywebservice.svg)](https://jitpack.io/#ashkanpower/easywebservice)

## How to use
Add jitpack to repositories
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
``````

then add library to dependencies

`````
dependencies {
	        implementation 'com.github.ashkanpower:easywebservice:57955d45b2'
	}
``````

## Fast useage

response : 
````
  {res : true, msg : "success"}
`````
code :
```JAVA
  new EasyWebservice("http://host.com/api/v1/test")
	.method(Method.POST) //default
        .addParam("id", 100) //adding params to body
        .call(new Callback.AB<Boolean, String>("res", "msg") { //should map response params
		@Override
		public void onSuccess(Boolean res, String msg) {
          
                        //you can work with res and msg which are in json response
		}

	        @Override
		public void onError(String error) {
          
                        //if any error encountered
		}
	});
```

## Object usage
If you have object in your response you should define that object


response : 
````
  { thePerson : {id:10, name : "ashkan", lastname : "power"} }
`````  

code : 

  ```JAVA
  //define your class with the same attributes as the JSON response
 public class Person {
    int id; 
    String name;
    String lastname;
  }
  
   new EasyWebservice("http://host.com/api/v1/person")
				.call(new Callback.A<Person>("thePerson") { //should map response params
					@Override
					public void onSuccess(Person person) {
          
                   //the person object filled with response info
					}

					@Override
					public void onError(String error) {
          
                    //if any error encountered
					}
				});
````

# note : The class must be accessible by gson library

## array responses 

EasyWebservice also handles array responses

response :
`````
["California", "New York", "LA", "Texas"]
``````
code :
```JAVA
new EasyWebservice("http://host.com/api/v1/cities")
				.call(new Callback.A<ArrayList<String>>() { //the mapping for root elements should be empty
					@Override
					public void onSuccess(ArrayList<String> cities) {
          
                   //work with the array
					}

					@Override
					public void onError(String error) {
          
                    //if any error encountered
					}
				});
```    

It can easily be also an array of custom objects like this.

response :
`````
[{id:10, name : "ashkan", lastname : "power"},
{id:10, name : "ashkan", lastname : "power"},
{id:10, name : "ashkan", lastname : "power"} ]
``````

code : 
```JAVA
class Person {
    int id; //the same as object
    String name;
    String lastname;
  }

new EasyWebservice("http://host.com/api/v1/cities")
				.call(new Callback.A<ArrayList<Person>>() { //the mapping for root elements should be empty
					@Override
					public void onSuccess(ArrayList<Person> people) {
          
                   //work with the array
					}

					@Override
					public void onError(String error) {
          
                    //if any error encountered
					}
				});
`````

## More info
The callback group has 5 levels

```JAVA
Callback.A
Callback.AB
Callback.ABC
Callback.ABCD
Callback.ABCDE
```
which allows you to get JSON response up to 5 root attributes.
the combination will be unlimited.

note : If you have more than 5 attributes, you should define a response class specified to that json;
