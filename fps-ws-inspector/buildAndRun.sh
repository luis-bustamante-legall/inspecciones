#!/bin/sh
mvn clean package && docker build -t pe.farmaciasperuanas.inspector/fps-ws-inspector .
docker rm -f fps-ws-inspector || true && docker run -d -p 8080:8080 -p 4848:4848 --name fps-ws-inspector pe.farmaciasperuanas.inspector/fps-ws-inspector 
