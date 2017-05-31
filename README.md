## Ease Volley Wrapper

**Ease** is a wrapper around [Volley](https://github.com/google/volley) for handling network 
responses more effectively. It offers **auto parsing** of response to a **specific** type with **clean**
and **intuitive** API for creating requests and handling responses.

By default, _Ease_ gives support for handling following kind of responses but configuring it 
for **any other** response is tremendously easy. If you have little experience with Java you can configure
 it for any type of response.

    {
 	    "result_description": "des",
 	    "data": {}
    }
                 
    {
  	    "result_description": "des",
  	    "data": [{}]
    }

## Why should I use Ease?

[Volley](https://github.com/google/volley) offers a simple API for handling network calls and responses, 
but for relatively bigger projects it is cumbersome to stick with default Volley API for handling 
network calls.

1. While Volley's toolbox supports many Request formats out of the box, it does not allow us to parse response 
to a **specific or desired type**. 

2. Volley's default response listener does not allow to handle multiple request with same callback handler.

3. When there is more than a single request on an Activity or Fragment, it pollutes code with anonymous 
handlers making it hard to read and maintain.

4. It does not support (by default) progress dialogs, you have to write your own for showing it before a 
network call.




> **Ease** is designed with having all the above issues in mind. It does most of the work on behalf of 
> developers, leaving behind the **minimal** work for them.



## Building a Request

Creating a request with *Ease* is literally easy. `RequestBuilder` offers numerous methods for 
tweaking a request. e.g. running in background, enable caching etc.

  1. Creating Request Headers *[Optional]*
  
      ```java
      RequestHeaders headers = RequestHeaders.newInstance().put("some", "value").put("key", "va");
      ```
  2. Creating Request Body *[Optional]*   
  
      ```java
       RequestBody body = RequestBody.newInstance().put("body", "value").put("other", "val");
      ```
  
  3. Creating Request
  
  
      ```java
        EaseRequest.type(new TypeToken<EaseResponse<List<UserModel>>>(){}) // type token discussed below
                        .method().post() // request type
                        .body(body) // body created in step 2
                        .headers(headers) // headers created in step 1
                        .requestId(100) // request ID for this request
                        .endPoint("users") 
                        .responseCallbacks(this) // callbacks
                        .build().execute(this); // this = context
      ```

## Creating type tokens

In order to parse a network response to arbitrary objects using *Gson* you need to pass `type information` of the `Object`
to `Ease`. This can be done with `TypeToken` class which accepts required type as parametrized type. e.g.

    new TypeToken<EaseResponse<List<UserModel>>>(){}

It will convert the *data* key from network response into a list of `UserModels`.

> This syntax is a little ugly but sadly, we have no other choice.

## Getting response as String

Typically `Ease` will convert network response to required type but sometimes you want it without conversion e.g. in case 
you want to receive many requests with same callback, taking the responsibility of converting *JSON* response
to model yourself. For this purpose, `Ease` provides `asString(Class<T>)` method for getting response as String.

      EaseRequest.asString(String.class)...

## Receiving Response

Ease offers a clean way for handling network response by using `EaseCallbacks` interface. For 
above example response can be handheld as following.

```java

   @Override
    public void onSuccess(@NonNull EaseRequest<List<UserModel>> request, @NonNull String description, @NonNull List<UserModel> data) {
            // on success, use request.id() for getting request id.
    }


    @Override
    public void onFailure(@NonNull EaseRequest<List<UserModel>> request, @NonNull String description) {
            // request failed with description
    }


    @Override
    public void onError(@NonNull EaseRequest<List<UserModel>> request, @NonNull EaseException e) {
            // error occured while connecting to server
    }
    
```


## Running network call in background

For running network calls in background, you can call `runInBackground()` while building network request. If 
network call is running in background **no progress dialog will be shown to user**.

## Getting Started

### Configuration

`Ease` is not smart enough to know every thing in advance. To help it out, you need to pass a configuration 
object *(implementation of `EaseConfig` interface)* to `Ease` for making it work according to your need.

Call `EaseUtils.init` at least once with `EaseConfig` object for configuring `Ease`.

    EaseUtils.init(EaseConfig)
    
        
> Please not if you don't initialize `Ease`, it will use default implementation of `EaseConfig`.        
    
Made with :heart: by allaudin