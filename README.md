# ORDER DATA CLEAN

Maintenance process to clean data in CloudSql Database, applied on a specific table and a period (days).
Mainly, to liberate disk space used by CloudSql.
[Developed with Springboot framework and compiled with Gradle.]

## Getting Started

Download the code from gitlab:
$ git clone ...

### Prerequisites

Gradle: 6.3+

IDE: Your preference (Intellij, VisualStudio Code)

Database: A CloudSql Database

Table: A table into the database with a column called created_at (timestamp type) and some dummy data.

### Installing

Open the project into your IDE

Compile the code:
```
$ gradle clean build
```

Config the running test (for Run or Debug)

## Running the tests

--

## Deployment

Set Environment Var:
```
$ export GOOGLE_APPLICATION_CREDENTIALS=[PATH_TO_JSON]/[JSON_FILE_NAME].json
$ export PROJECT_ID=[THE_PROJECT_ID] 
```

Deploy the project locally using:
```
$ java -jar build/libs/[JAR_NAME].jar
```

Deploy the project remotely:

In Google Cloud Platform, Using CloudRun

1.- Build and Upload the Image to Google Cloud Repository
```
$ gcloud builds submit - tag gcr.io/[GCP_PROJECT_ID]/[IMAGE_NAME]

```

2.- Create a CloudRun services using the image 
```
$ gcloud run deploy - image gcr.io/[GCP-PROJECT-ID]/[CLOUDRUN_SERVICE_NAME] - platform managed - service-account [MY-SERVICE-ACCOUNT]@[GCP-PROJECT-ID].iam.gserviceaccount.com - memory 512 - max-instances 5

```

## Executing
Local
```
curl http://[HOST]:[PORT]/order
```

CloudRun
```
curl https://[CLOUD_RUN_SERVICE_ID].a.run.app/order
```

Result Sample:
```{"status":true,"rows":271}```

## Built With

* [Gradle](https://gradle.org/) - Dependency Management
* [SpringBoot](https://spring.io/projects/spring-boot) - The Web Framework

## Contributing

Anyone who want to contribute can do it pushing the upgrade in the respective branch.

## Versioning

v1.0.0

## Authors

* **Oliver Fierro Villalobos** - *Initial work* - [GitHub](https://github.com/oliverfierro77)

Contributors:
--

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

--

