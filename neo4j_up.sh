#!/usr/bin/env bash

BASEDIR=$(realpath "$(dirname "$0")")

. "${BASEDIR}/CONTAINER.conf"

docker run \
  --rm \
  --name "$CONTAINER_NAME" \
  --publish=7474:7474 --publish=7687:7687 \
  --env NEO4J_AUTH=neo4j/"$PASSWORD" \
  neo4j:latest
