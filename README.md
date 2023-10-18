# aerie-simple-model-data
A reusable multi-mission model of data management for Aerie

This repository provides a simple, configurable spacecraft data management model for use within the [Aerie framework](https://github.com/NASA-AMMOS/aerie). The data model includes basic components to represent a data storage device and categories of data, which can be optionally storage limited and represent types of data, folders, partitions, etc.

## Quick Start

Interested in giving the data model a quick spin? We've pre-built a very simple example spacecraft model, demosystem, that uses the data model. This model has a few activities in it that add and transfer data. To try out this model, simply load [demosystem.jar](demosystem.jar) into Aerie. If you have never used Aerie before and need some help getting it deployed and uploading a model, start [here](https://nasa-ammos.github.io/aerie-docs/introduction/#fast-track).

Once you have loaded [demosystem.jar](demosystem.jar) into Aerie, make a plan with a time range of your choosing (a plan length of a day should be reasonable). Once you have created a plan, add a couple of activities to it (e.g. TurnOnCamera) and hit the simulate button. Once the simulation completes you should see a green check appear next to the Simulation icon, and viola, you have successfully run the data model!

To actually see the results of the simulation you ran, you can load a pre-built [basic data model view](DataModelBasicView.json) that will place a number of different resources on to the timeline for named buckets of data.

There are number of configuration variables available in the "Simulation" pane that you can adjust to produce different behavior with the data model. Feel free to play around with these configurations and re-simulate to see how the results change.

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

