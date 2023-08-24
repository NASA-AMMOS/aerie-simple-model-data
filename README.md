# aerie-data-model
A reusable multi-mission model of data management for Aerie


## Organization

The core data model is in the `model` directory.  This is what the mission modeler would integrate into their own spacecraft model if they needed a data model. The `demo` directory contains an example to show how a mission modeler can integrate this data model into their model, specifically by changing their package.info file and their top-level mission class. 

The following instructions assume that you are using MacOS, but the instructions for building and running should work on Linux and Windows, too.
## Prerequisites

- Install [OpenJDK Temurin LTS](https://adoptium.net/temurin/releases/?version=19). If you're on macOS, you can install [brew](https://brew.sh/) instead and then use the following command to install JDK 19:

  ```sh
  brew tap homebrew/cask-versions
  brew install --cask temurin19
  ```

  Make sure you update your `JAVA_HOME` environment variable. For example with [Zsh](https://www.zsh.org/) you can update your `.zshrc` with:

  ```sh
  export JAVA_HOME="/Library/Java/JavaVirtualMachines/temurin-19.jdk/Contents/Home"
  ```

- Ensure you have docker installed on your machine. If you do not, install it [here](https://docs.docker.com/desktop/).

- Navigate to a directory on your local machine where you want to keep this repo and clone the repo using the following command:

  ```sh
  git clone https://github.jpl.nasa.gov/MPS/aerie-data-model.git
  ```

- Set `GITHUB_USER` and `GITHUB_TOKEN` environment variables to your credentials inside this directory (first you need to create a [personal access token](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens#creating-a-personal-access-token-classic) in your GitHub account) so you can download the Aerie Maven packages from the [GitHub Maven package registry](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry). For example with Zsh you can update your `.zshrc` to set the variables with:

  ```sh
  export GITHUB_USER="your github user"
  export GITHUB_TOKEN="your github token"
  ```

## Building

To build the mission model JAR file in the `demo/build/libs` sub-directory of the repository directory and the data model jar in the `model/build/libs` ub-irectory, you can type in this command in the directory of the repository:

```sh
./gradlew build --refresh-dependencies
```

You can deploy Aerie on your local machine by first opening Docker Desktop, and then you can start the Aerie services using the following command in the directory of the repository:

```sh
docker compose up
```

You can then upload the JAR to Aerie using either the [UI](http://localhost/) or API. 

## Testing

To run unit tests under [./src/test](./src/test), you can enter:

```sh
./gradlew test
```

