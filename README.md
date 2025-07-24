[![Version](https://img.shields.io/maven-central/v/io.github.easyretrofit/retrofit-spring-boot-web-starter?logo=apache-maven&style=flat-square)](https://central.sonatype.com/artifact/io.github.easyretrofit/retrofit-spring-boot-web-starter)
[![Build](https://github.com/easyretrofit/retrofit-spring-boot-web-starter/actions/workflows/build.yml/badge.svg)](https://github.com/easyretrofit/retrofit-spring-boot-web-starter/actions/workflows/build.yml/badge.svg)
[![License](https://img.shields.io/github/license/easyretrofit/spring-boot-starter.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![License](https://img.shields.io/badge/JDK-8+-4EB1BA.svg)](https://docs.oracle.com/javase/8/)
[![License](https://img.shields.io/badge/spring--boot-2.0.0+-green.svg)]()



# retrofit-spring-boot-web-starter
retrofit-spring-boot-web-starter

## Install

**Maven:**

```xml

<dependency>
    <groupId>io.github.easyretrofit</groupId>
    <artifactId>retrofit-spring-boot-web-starter</artifactId>
    <version>${latest-version}</version>
</dependency>
```

**Gradle:**

```groovy
dependencies {
    implementation 'io.github.easyretrofit:retrofit-spring-boot-web-starter:${latest-version}'
}
```

## Usage
use can following:
https://github.com/liuziyuan/easy-retrofit-demo/tree/main/retrofit-spring-boot-web-starter-sample
support annotation:
```text
@AnyConverters.Json() // default jackson
@AnyConverters.Json(JsonConverter.GSON)
@AnyConverters.Json(JsonConverter.JACKSON)
@AnyConverters.Text()
@AnyConverters.Xml()
@AnyConverters.Protocol(ProtocolConverter.PROTOBUF)
@AnyConverters.Protocol(ProtocolConverter.WIRE)

```
```java
    //by default, if you don't have annotation, it will use jackson converter
    @GET("hello/{message}")
    HelloBean hello(@Path("message") String message);


    @AnyConverters.Json(JsonConverter.GSON)
    @GET("hello/{message}")
    HelloBean hello2(@Path("message") String message);
```

Support Adapter:

- [x] SimpleBody
- [x] Guava
- [x] RxJava3
- [x] Reactor

Support Converter:

- [x] Jackson
- [x] Gson
- [x] Jaxb
- [x] PROTOBUF
- [x] WIRE

##  Example

By default, you do not need any Converter annotations and will use Json (Jackson) as the default Converter

If you want to use another parser for your interface, you can use it this way,

@AnyConverters.Text() // using TextConverter

@AnyConverters.Protobuf() // using ProtobufConverter

@AnyConverters.Protobuf(value = ProtocolConverter.PROTOBUF) // using ProtobufConverter

@AnyConverters.Protobuf(value = ProtocolConverter.WIRE) // using WireConverter

@AnyConverters.Xml() // using XmlConverter

@AnyConverters.Json() // using JacksonConverter

@AnyConverters.Json(value = JsonConverter.JACKSON) // using JacksonConverter

@AnyConverters.Json(value = JsonConverter.GSON) // using GsonConverter

```java
@AnyConverters.Text()
@Headers("Content-Type: text/plain")
@GET("/job/{jobName}/lastBuild/buildNumber")
Integer lastBuildNumber(@Path("jobName") String jobName);
```


## multiple API merge support
Rxjava3: return MyBean

```java
Flowable<ResponseResult<List<String>>> flowable1 = productApi.rxjava3Products().onErrorReturn(throwable -> {
    return ResponseResult.failure(500, "Error occurred");
});
Flowable<ResponseResult<List<Integer>>> flowable2 = productApi.rxjava3ProductsInt();
// 合并多个 Flowable
Flowable<MyBean> mergedFlowable = Flowable.zip(flowable1, flowable2, (result1, result2) -> {
    List<String> strings = result1.getData();
    List<Integer> integers = result2.getData();

    // 创建 MyBean 对象
    return new MyBean(strings, integers);
});
return mergedFlowable.blockingFirst();
```

Java: return MyBean
```java
CompletableFuture<ResponseResult<List<String>>> future = productApi.productsAsync();
CompletableFuture<ResponseResult<List<Integer>>> future2 = productApi.productsAsyncInt();
CompletableFuture<Void> allFutures = CompletableFuture.allOf(future, future2);
CompletableFuture<MyBean> resultFuture = allFutures.thenApply(v -> {
    try {
        List<String> listResponseResult = future.get().getData();
        List<Integer> listResponseResult1 = future2.get().getData();

        return new MyBean(listResponseResult, listResponseResult1);
    } catch (InterruptedException | ExecutionException e) {
        throw new RuntimeException(e);
    }
});
return resultFuture.get();
```

guava: return MyBean
```java
ListenableFuture<ResponseResult<List<String>>> flowable1 = productApi.productGuava();
ListenableFuture<ResponseResult<List<Integer>>> flowable2 = productApi.productGuavaInt();
// 使用 Futures.allAsList 来等待所有 ListenableFuture 完成
// 使用 Futures.whenAllComplete 来等待所有 ListenableFuture 完成
return Futures.whenAllComplete(flowable1, flowable2)
.call(() -> {
    ResponseResult<List<String>> result1 = flowable1.get();
    ResponseResult<List<Integer>> result2 = flowable2.get();

    List<String> stringList = result1.getData();
    List<Integer> integerList = result2.getData();

    return new MyBean(stringList, integerList);
}, MoreExecutors.directExecutor()).get();
```

reactor: return Mono<List<HelloBean>>
```java
Mono<HelloBean> mono = helloApi.hello(message);
Mono<HelloBean> Mono1 = helloApi.hello(message);

Mono<List<HelloBean>> zip = Mono.zip(mono, Mono1, (helloBean, helloBean2) -> {
    return List.of(helloBean, helloBean2);
});
return zip;
```


