#!/bin/bash

##############################################################
# announce_to_github.sh
#
# When invoked as part of a Travis CI build, detects whether
# the current build is a result of the creation of a tag and,
# if so, announces the creation of that tag to the configured
# Slack channels.
#
# Required environmental variables:
# - SLACK_API_TOKEN: The API token of the Slack app that this
#   script will make the announcement as.
# - SLACK_TARGET_CHANNELS: A comma-separated list of Slack
#   channels to which the announcement will be made.
#
# Note that the Slack API token must have the permission scope
# 'chat:write:bot'.
##############################################################

GITHUB_REPO_URL=https://github.com/JiscRDSS/rdss-message-api-docs

log() {
  if [ "$1" = "ERROR" ]; then
    echo "[$(date +"%Y-%m-%d %H:%M:%S")] [ERROR] $2"
  else
    echo "[$(date +"%Y-%m-%d %H:%M:%S")] [INFO] $2"
  fi
}

log_info() {
  log "INFO" "$1"
}

log_error() {
  log "ERROR" "$1"
}

build_announcement_text() {
  echo "A new release of the Message API Specification has been created: <$GITHUB_REPO_URL/releases/tag/${TRAVIS_TAG}|$TRAVIS_TAG>." \
       "%0AYou can infer the impact of this release from the version, see http://semver.org/." \
       "%0AModifications to bring your application up to date with this standard will be dispatched via Sprint Planning, however please reach out via Slack if there's any issues in the mean time." \
       "%0AIf you would like to influence the develop process for future versions, please get involved via GitHub <$GITHUB_REPO_URL/issues|Issues> and <$GITHUB_REPO_URL/pulls|Pull Requests>."
}

execute_announcement () {
  curl -X POST \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "token=$SLACK_API_TOKEN" \
    -d "as_user=false" \
    -d "username=Message API Specification Bot" \
    -d "channel=$1" \
    -d "text=$2" \
    https://slack.com/api/chat.postMessage
}

if [ ! -z "$TRAVIS_TAG" ]
then
  log_info "Announcing the release of '$TRAVIS_TAG' to Slack channels '$SLACK_TARGET_CHANNELS'..."
  for SLACK_CHANNEL in ${SLACK_TARGET_CHANNELS//,/ }
  do
    log_info "Announcing the release of '$TRAVIS_TAG' to Slack channel '$SLACK_CHANNEL'..."
    execute_announcement "$SLACK_CHANNEL" "$(build_announcement_text)"
  done
else
  log_info "Got \$TRAVIS_TAG value '$TRAVIS_TAG' - \$TRAVIS_BRANCH '$TRAVIS_BRANCH' is not a candidate for announcement."
fi

exit 0
