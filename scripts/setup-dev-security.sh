#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

AUTH_CERT_DIR="$ROOT_DIR/services/auth-service/src/main/resources/certs"
GATEWAY_CERT_DIR="$ROOT_DIR/services/api-gateway/api-gateway/src/main/resources/certs"

KEYSTORE="$AUTH_CERT_DIR/jwt-keystore.p12"
PUBLIC_CERT="$AUTH_CERT_DIR/public-cert.pem"
GATEWAY_PUBLIC_CERT="$GATEWAY_CERT_DIR/public-cert.pem"

KEY_ALIAS="jwt-key"

echo "Setting up development JWT security..."

if ! command -v keytool >/dev/null 2>&1; then
    echo "ERROR: keytool is required but was not found."
    echo "Install Java 21+ and try again."
    exit 1
fi

mkdir -p "$AUTH_CERT_DIR"
mkdir -p "$GATEWAY_CERT_DIR"

if [[ -z "${JWT_KEYSTORE_PASSWORD:-}" ]]; then
    JWT_KEYSTORE_PASSWORD="devpassword"
    export JWT_KEYSTORE_PASSWORD
    echo "Using default development keystore password."
fi

if [[ ! -f "$KEYSTORE" ]]; then
    echo "Generating development JWT keystore..."

    keytool -genkeypair \
        -alias "$KEY_ALIAS" \
        -keyalg RSA \
        -keysize 2048 \
        -storetype PKCS12 \
        -keystore "$KEYSTORE" \
        -storepass "$JWT_KEYSTORE_PASSWORD" \
        -keypass "$JWT_KEYSTORE_PASSWORD" \
        -validity 3650 \
        -dname "CN=ecommerce-auth"
else
    echo "JWT keystore already exists. Keeping existing key."
fi

echo "Generating public certificate..."

keytool -exportcert \
    -rfc \
    -alias "$KEY_ALIAS" \
    -keystore "$KEYSTORE" \
    -storepass "$JWT_KEYSTORE_PASSWORD" \
    -file "$PUBLIC_CERT"

cp "$PUBLIC_CERT" "$GATEWAY_PUBLIC_CERT"

echo
echo "Development JWT security setup complete."
echo
echo "Keystore:"
echo "  $KEYSTORE"
echo
echo "Gateway public certificate:"
echo "  $GATEWAY_PUBLIC_CERT"
echo
echo "JWT_KEYSTORE_PASSWORD=$JWT_KEYSTORE_PASSWORD"
