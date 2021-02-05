This project demonstrates [Tapir].

All steps are guided by `podcast.infrastructure.api.PodcastApiSpec`. Please run this test and let if fail.

## Step 1: get Tapir dependency

Many of tapir functionalities come as builder methods in the main package, hence it’s easiest to work  with tapir if you
import the main package entirely, i.e.:

```scala
import sttp.tapir._
```

## Step 2: define your first endpoint

We will define our first endpoint. It takes no parameters.

## Step 3: use Play Json library to define an output format

Tapir support [many JSON libraries][tapirJSON]. We will use Play JSON to define output format.  

## Step 4: expose endpoint with akka-http 

With an endpoint definition, we can add a specific implementation and wire it to a server like akka-http.

## Step 5: generate open api

Starting from endpoints, you can generate openapi contract automatically.

## Step 6: expose OpenAPI with swagger-ui

Tapir lets you expose your OpenAPI on your web server.

[Tapir]: https://tapir.softwaremill.com
[TapirJSON]: https://tapir.softwaremill.com/en/latest/endpoint/json.html
