This project demonstrates [Tapir].

All steps are guided by `podcast.infrastructure.api.PodcastApiSuite`. Please run this test and let if fail.

## Step 1: get Tapir dependency

Many of tapir functionalities come as builder methods in the main package, hence it’s easiest to work with tapir if you
import the main package entirely, i.e.:

```scala
import sttp.tapir.*
```

## Step 2: define your first endpoint

We will define our first endpoint. It takes no parameters.

## Step 3: use Play Json library

Tapir support [many JSON libraries][tapirJSON]. We will use Play JSON to define output format.

## Step 4: define endpoint’s output

The endpoint will have to produce a JSON body as a JSON Object with dynamic keys and JSON Numbers as values. We will
define this contract with Tapir.

## Step 5: define endpoint’s server logic

An endpoint can be derived to be a server implementation by adding server logic.

## Step 6: get Tapir’s pekko-http server interpreter dependency

In order for pekko-http to automatically convert ServerEndpoints to pekko-http route, you have to import a new
dependency. Then you can use this object:

```scala
sttp.tapir.server.pekkohttp.PekkoHttpServerInterpreter
```

Please have a look to [documentation][TapirPekkoHttp] for more information.

## Step 7: use Tapir’s pekko-http server interpreter to declare route

Add endpoint implementation to pekko-http’s routes:

```scala
} ~ PekkoHttpServerInterpreter.toRoute(…)
```

## Step 8: generate open api

Starting from endpoints, you can [generate openapi contract automatically][TapirOpenAPI].

## Step 9: Expose open-api with swagger-ui

Tapir lets you expose your OpenAPI on your web server.

[Tapir]: https://tapir.softwaremill.com
[TapirJSON]: https://tapir.softwaremill.com/en/latest/endpoint/json.html
[TapirOpenAPI]: https://tapir.softwaremill.com/en/latest/docs/openapi.html
[TapirPekkoHttp]: https://tapir.softwaremill.com/en/latest/server/pekkohttp.html
