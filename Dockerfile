# 采用java官方镜像做为构建镜像
FROM maven:3.6.0-jdk-8-slim AS build

# 设置应用工作目录
WORKDIR /app

# 将所有文件拷贝到容器中
COPY . .

# 编译项目
RUN mvn -B -e -U -s settings.xml clean package

# 采用java或者alpine官方镜像做为运行时镜像
FROM alpine:3.13

# 设置应用工作目录
WORKDIR /app

# 将构建产物拷贝到运行时的工作目录中
COPY --from=build /app/**/*.jar ./

# 安装基础命令
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories && \
    apk add --update --no-cache ca-certificates curl openjdk8-jre-base tzdata && \
    rm -f /var/cache/apk/*

# 设置时区
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo '$TZ' > /etc/timezone

# 运行启动命令
ENTRYPOINT ["java", "-Xmx2G", "-jar", "superapp-0.0.1-SNAPSHOT.jar"]
