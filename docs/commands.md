### Docker ###
# Start the docker - helps in creating if not found, starting if it exists #
cd infra
docker compose up -d

# Status Check #
docker ps

# logs #
docker logs ecommerce-postgres

# check database #
docker exec -it ecommerce-postgres psql -U postgres
docker exec -it ecommerce-postgres psql -U postgres -d auth_db
