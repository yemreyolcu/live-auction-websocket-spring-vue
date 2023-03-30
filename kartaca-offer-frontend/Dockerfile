FROM node:latest AS build
WORKDIR /usr/src/app
COPY package.json .
RUN npm install
COPY . .
RUN npm run build

FROM nginx:latest
WORKDIR /usr/share/nginx/html
COPY --from=build /usr/src/app/dist/ .
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 80
