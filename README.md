This project demonstrates [Tapir].

All steps are guided by `podcast.infrastructure.api.PodcastApiSuite`. Please run this test and let if fail.

## Step 1: get Tapir dependency

Many of tapir functionalities come as builder methods in the main package, hence it’s easiest to work with tapir if you
import the main package entirely, i.e.:

```scala
import sttp.tapir.*
```

[Tapir]: https://tapir.softwaremill.com
