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

[Tapir]: https://tapir.softwaremill.com
[TapirJSON]: https://tapir.softwaremill.com/en/latest/endpoint/json.html
