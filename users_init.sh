#!/bin/bash
set -e

# dbUser is the userName used from applicatoin code to interact with databases and dbPwd is the password for this user.
# MONGO_INITDB_ROOT_USERNAME & MONGO_INITDB_ROOT_PASSWORD is the config for db admin.
# admin user is expected to be already created when this script executes. We use it here to authenticate as admin to create
# dbUser and databases.

echo ">>>>>>> trying to create database and users"
if [ -n "${MONGO_INITDB_ROOT_USERNAME:-}" ] && [ -n "${MONGO_INITDB_ROOT_PASSWORD:-}" ] && [ -n "${MONGO_INITDB_DATABASE:-}" ] && [ -n "${MONGO_DB_USERNAME:-}" ] && [ -n "${MONGO_DB_PASSWORD:-}" ]; then
mongo -u $MONGO_INITDB_ROOT_USERNAME -p $MONGO_INITDB_ROOT_PASSWORD<<EOF
db=db.getSiblingDB('$MONGO_INITDB_DATABASE');
use $MONGO_INITDB_DATABASE;
db.createUser({
  user:  '$MONGO_DB_USERNAME',
  pwd: '$MONGO_DB_PASSWORD',
  roles: [{
    role: 'readWrite',
    db: '$MONGO_INITDB_DATABASE'
  }]
});
EOF
else
    echo "MONGO_INITDB_ROOT_USERNAME,MONGO_INITDB_ROOT_PASSWORD,MONGO_INITDB_DATABASE, MONGO_DB_USERNAME and MONGO_DB_PASSWORD must be provided. Some of these are missing, hence exiting database and user creatioin"
    exit 403
fi