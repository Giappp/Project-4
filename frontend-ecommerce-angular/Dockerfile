# Step 1: Use a base image for Nginx
FROM nginx:alpine

# Copy the built files from the build stage to the Nginx directory
COPY dist/frontend-ecommerce-angular /usr/share/nginx/html

# COPY ./nginx.conf /etc/nginx/nginx.conf


EXPOSE 80

# Command to start Nginx
CMD ["nginx", "-g", "daemon off;"]
