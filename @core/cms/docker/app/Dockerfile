FROM node:18

WORKDIR /usr/src/app

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build

RUN wget https://releases.hashicorp.com/vault/1.15.4/vault_1.15.4_linux_amd64.zip && \
    unzip vault_1.15.4_linux_amd64.zip && \
    mv vault /usr/local/bin/ && \
    rm -f vault_1.15.4_linux_amd64.zip

COPY src/scripts/init-vault.sh /usr/local/bin/init-vault.sh

RUN chmod +x /usr/local/bin/init-vault.sh

EXPOSE 3000

ENV NODE_ENV production

CMD ["node", "dist/main"]

