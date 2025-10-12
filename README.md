<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">

</head>
<body>
    <header>
        <h1>My HTTP Server</h1>
        <p>A lightweight Java HTTP server for serving static HTML files</p>
    </header>


structure</h2>
            <pre>
Http_Server/
├─ src/
│  ├─ main/
│  │  ├─ java/com/haddaji/httpserver/...
│  │  └─ resources/http.json
│  └─ webRoot/index.html
├─ target/ (Maven build output)
└─ Dockerfile
            </pre>
        </section>

{
  "port": 8080,
  "webroot": "webRoot"
}
            </pre>
        </section>


# Pull the image
```bash
docker pull had683/haddaji-http-server
```
# Run the container
```bash
docker run -dp 8080:8080 --name <<chose_your_name>> hadd683/haddaji-http-server
```
# Open in browser
http://localhost:8080
            </pre>

        
       
# Build the Docker image locally
```bash
docker build -t haddaji-http-server .
```
# Run the container
```bash
docker run -dp 8080:8080 --name <<chose_your_name>> haddaji-http-server
```
# Open in browser
http://localhost:8080
            </pre>
            



</body>
</html>
