#!/bin/bash
set -e # Exit with nonzero exit code if anything fails

REPO=`git config remote.origin.url`
SSH_REPO=${REPO/https:\/\/github.com\//git@github.com:}
TAXONOMY_REPO_URL=https://github.com/JiscRDSS/taxonomyschema

git config user.name "Travis CI"
git config user.email "deploy@travis-ci.org"

if [ ! -z "$TRAVIS_TAG" ]
then
  ENUM_JSON_PATH=$(pwd)/schemas/enumeration.json
  # Clone the Taxonomy Schema repo into a temp directory 
  TEMPPATH=$(mktemp -d)
  cd $TEMPPATH
  git clone $TAXONOMY_REPO_URL
  cd ./taxonomyschema

  # Checkout a new feature branch named with this tagged release 
  BRANCHNAME="feature/${TRAVIS_TAG}-rdss-message-api-docs"
  git checkout -b $BRANCHNAME 

  # Update the data models in taxonomyschema 
  python ./update_data_models.py $ENUM_JSON_PATH --output_path ./datamodels 

  # Commit the resulting updates 
  git add -A .
  git commit -m "Automatic update of taxonomy schema from ${TRAVIS_REPO_SLUG}/${TRAVIS_TAG}" 

  # Get the deploy key by using Travis's stored variables to decrypt deploy_key.enc
  ENCRYPTED_KEY_VAR="encrypted_${ENCRYPTION_LABEL}_key"
  ENCRYPTED_IV_VAR="encrypted_${ENCRYPTION_LABEL}_iv"
  ENCRYPTED_KEY=${!ENCRYPTED_KEY_VAR}
  ENCRYPTED_IV=${!ENCRYPTED_IV_VAR}
  openssl aes-256-cbc -K $ENCRYPTED_KEY -iv $ENCRYPTED_IV -in ../github_deploy_key.enc -out ../github_deploy_key -d
  chmod 600 ../github_deploy_key
  eval `ssh-agent -s`
  ssh-add ../github_deploy_key

  # Push the newly created feature branch.
  git push $SSH_REPO $BRANCHNAME
fi
