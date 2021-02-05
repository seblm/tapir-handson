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

[Tapir]: https://tapir.softwaremill.com
