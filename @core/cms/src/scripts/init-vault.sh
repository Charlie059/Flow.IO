#!/bin/sh

export VAULT_ADDR=http://vault:8200


sleep 5 
vault login myroot
vault secrets enable transit
