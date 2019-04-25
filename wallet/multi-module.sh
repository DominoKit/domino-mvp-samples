#!/bin/bash
mvn archetype:generate \
    -DarchetypeGroupId=org.dominokit.domino.archetypes \
    -DarchetypeArtifactId=domino-gwt-module-archetype \
    -DarchetypeVersion=1.0-rc.4-SNAPSHOT \
    -DarchetypeParentGroupId=org.dominokit.tutorials \
    -DarchetypeParentArtifactId=wallet \
    -DarchetypeParentVersion=1.0-SNAPSHOT \
    -DgroupId=org.dominokit.tutorials \
    -DartifactId=$1 \
    -Dmodule=${1^} \
    -Dsubpackage=$1
