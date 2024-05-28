## Design notes
Please refer to the [design_notes.pdf](https://github.com/devaldana/registry/blob/master/design_notes.pdf)
file present in the root directory of this project.
## Quick start

- Only Docker is required to be installed.

### Run prebuilt image

Open a terminal (Unix/Linux/MacOS) or a Command Prompt (Windows) and then execute:
```bash
docker pull univ3036/registry && docker run --rm -it -p 8080:8080 univ3036/registry reg
```
* For PowerShell (Windows) terminal replace `&&` by `;` or executed one command at a time.

### Go to http://localhost:8080
There you have a sample request, click on "Send Request" and see the result in the
right panel. Click "Connect" in the bottom panel to query the database.

#### Sample database query
```sql
SELECT * FROM USERS;
```

## Compiling, building and executing Dockerfile
In case you want to compile the code, create the image and spin up a container, then
execute:
```bash
docker build -t reg . && docker run --rm -it -p 8080:8080 reg
```
- For PowerShell (Windows) terminal replace `&&` by `;` or executed one command at a time.

## What if I don't have Docker installed?
Optionally, you can install Java 22 and execute it using any of the next options:
- `mvn clean spring-boot:run` - Requires Maven installed.
- `./mvnw clean spring-boot:run` - Only working on Unix (Linux/MacOS)