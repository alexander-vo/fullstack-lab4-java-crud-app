FROM java:8-jdk-alpine

# Install needed packages
RUN apk add --no-cache curl tar bash procps

# Download and install Maven
ARG MAVEN_VERSION=3.6.3

ARG USER_HOME_DIR="/root"

# Maven URL download
ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

# Maven variables
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

# Application folder
RUN mkdir /app
WORKDIR /app
COPY . .

# Build app and install dependencies
RUN apk update
RUN mvn clean install
RUN mvn dependency:copy-dependencies
