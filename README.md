# ChatGPT(glm) AI问答项目

**《ChatGPT（glm） AI 问答助手》** 开源项目，涵盖爬虫接口、ChatGPT API对接、DDD架构设计、镜像打包、Docker容器部署，小巧精悍，流程全面。对于Java编程伙伴来说，非常具有学习价值。

技术栈：Java、SpringBoot、爬虫、ChatGPT、job、Docker

去到第5点看大概效果



## 1.工程创建和仓库使用

github首先建一个库

![image-20231106170746858](https://raw.sevencdn.com/Bo-Vane/picgo/main/img/202311061707600.png)

然后从idea拉取项目就行了，但是拉取项目的问题是我们没有src和maven工程目录，只有readme文件和一些基础文件，这时候可以通过idea创建工程项目把文件夹复制过来即可。

![image-20231106172532452](https://raw.sevencdn.com/Bo-Vane/picgo/main/img/202311061725920.png)

然后load一下maven就行

但是一般来说我们不用src目录，只需要pom.xml,所以把src删了，在项目下新建一个module就可以了

domain领域层：

![image-20231106173245991](https://raw.sevencdn.com/Bo-Vane/picgo/main/img/202311061732448.png)

interface接口层和application应用层，infrastructure基础层也一样，新建一个module

然后我们需要在父maven工程pom文件中引入springboot的一些配置和其他相关包

```xml
<parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.10</version>
        <relativePath/> <!-- lookup parent from repository -->
</parent>

<!--这里只是定义版本号，并不是真正的引入包    -->
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.58</version>
        </dependency>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.8</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.github.bonigarcia</groupId>
            <artifactId>webdrivermanager</artifactId>
            <version>5.0.3</version>
        </dependency>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
            <version>3.141.59</version>
        </dependency>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-api</artifactId>
            <version>3.141.59</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient -->
        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpclient</artifactId>
            <version>4.5.14</version>
        </dependency>
        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpmime</artifactId>
            <version>4.5.10</version>
        </dependency>
        <dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
            <version>RELEASE</version>
            <scope>test</scope>
        </dependency>
        <!-- https://mvnrepository.com/artifact/net.sf.json-lib/json-lib -->
        <dependency>
            <groupId>net.sf.json-lib</groupId>
            <artifactId>json-lib</artifactId>
            <version>2.2.3</version>
            <classifier>jdk15</classifier><!-- jdk版本 -->
        </dependency>
        <!-- https://mvnrepository.com/artifact/cn.hutool/hutool-all -->
        <dependency>
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
            <version>5.8.10</version>
        </dependency>
    </dependencies>
</dependencyManagement>

<build>
    <finalName>chatbot-api</finalName>
    <resources>
        <resource>
            <directory>src/main/resources</directory>
            <filtering>true</filtering>
            <includes>
                <include>**/**</include>
            </includes>
        </resource>
    </resources>
    <testResources>
        <testResource>
            <directory>src/test/resources</directory>
            <filtering>true</filtering>
            <includes>
                <include>**/**</include>
            </includes>
        </testResource>
    </testResources>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>2.12.4</version>
            <configuration>
                <skipTests>true</skipTests>
            </configuration>
        </plugin>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <configuration>
                <source>8</source>
                <target>8</target>
            </configuration>
        </plugin>
    </plugins>
</build>
```

注意<dependencyManagement>只是管理版本并不是真的引入包所以我们要在子module引入但不用写版本

比如我们需要在interface层引入依赖：

### 1.1接口层interface

```xml
<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
        <dependency>
            <groupId>io.github.bonigarcia</groupId>
            <artifactId>webdrivermanager</artifactId>
        </dependency>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
        </dependency>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-api</artifactId>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient -->
        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpclient</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpmime</artifactId>
        </dependency>
        <dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
            <version>RELEASE</version>
            <scope>test</scope>
        </dependency>
        <!-- https://mvnrepository.com/artifact/net.sf.json-lib/json-lib -->
        <dependency>
            <groupId>net.sf.json-lib</groupId>
            <artifactId>json-lib</artifactId>
            <classifier>jdk15</classifier><!-- jdk版本 -->
        </dependency>
        <!-- https://mvnrepository.com/artifact/cn.hutool/hutool-all -->
        <dependency>
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
        </dependency>

        <dependency>
            <groupId>com.bo</groupId>
            <artifactId>chatbot-api-domain</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>com.bo</groupId>
            <artifactId>chatbot-api-application</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>
```

注意后面是引入了chatbot-api-domain和chatbot-api-application

然后是接口层的启动类：

![image-20231106181251016](https://raw.sevencdn.com/Bo-Vane/picgo/main/img/202311061812055.png)

类的内容：

```java
/**
 * @author bovane
 * @description 启动入口
 * @github <a href="https://github.com/Bo-Vane/Chatbot-api">...</a>
 * @Copyright 2023
 */
@SpringBootApplication
public class ApiApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class,args);
    }
    
}
```

这里其实相当于我们在手动配置springboot的启动类和配置文件，而不是原来的通过框架导入，所以我们也需要在resources目录手动添加配置文件application.yml。

再去测试的java目录下添加com.bo.chatbot.api.test包



### 1.2 基础层infrastructure

除了接口层都不用resources目录和test目录，可以删掉。

然后在基础层包下com.bo.chatbot.api.infrastructure新建package-info的类：

![image-20231107100639809](https://raw.sevencdn.com/Bo-Vane/picgo/main/img/202311071006425.png)



### 1.3 领域层domain

和基础层infrastructure一样，建包就行



### 1.4 应用层application

和基础层infrastructure一样，建包就行



整个工程框架建好之后呢可以commit一下。为了以后方便呢在整个项目下添加docs文件夹，后续会有文档可以放在里面



## 2.创建知识星球，爬取接口信息

在知识星球APP先创建知识星球，方便爬取接口自己开发使用。

然后我们可以在星球中对自己提问采集接口信息，用F12，点击一个接口(这里回复一个1)，爬取接口信息：

![image-20231107131708987](https://raw.sevencdn.com/Bo-Vane/picgo/main/img/202311071317910.png)

接下来可以去测试类创建一个ApiTest类来爬取"**只看星主**"接口信息以获取问题

```java
@Test
public void queryQuestion() throws IOException {
    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/88885181481252/topics?scope=by_owner&count=20");
    get.addHeader("cookie",cookie");
    get.addHeader("Content-Type","application/json; charset=UTF-8");

    CloseableHttpResponse response = httpClient.execute(get);
    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
        String res = EntityUtils.toString(response.getEntity());
        System.out.println(res);
    }else {
        System.out.println(response.getStatusLine().getStatusCode());
    }
}
```

运行一下该方法，成功输出结果：

```json
{"succeeded":true,"resp_data":{"topics":[{"topic_id":188415855512582,"group":{"group_id":88885181481252,"name":"ChatGPT AI\u95ee\u7b54\u52a9\u624b","type":"pay","background_url":"https:\/\/images.zsxq.com\/Fkf5upPCNKZ8Gq_2BkZl-LuOKskK?e=1704038399&token=kIxbL07-8jAj8w1n4s9zv64FuZZNEATmlU_Vm6zD:Yhii7s_xOL0Le9nIvp--xNwR0as="},"type":"talk","talk":{"owner":{"user_id":415881842415518,"name":"\u4e0e\u53ef","avatar_url":"https:\/\/images.zsxq.com\/FuZZRlJTJ0drYd4ehGW0oEIK0OXZ?e=1704038399&token=kIxbL07-8jAj8w1n4s9zv64FuZZNEATmlU_Vm6zD:hZsDa7bttAQU_zv6fvdHghakuQ4=","location":"\u56db\u5ddd"},"text":"\u6b22\u8fce\u52a0\u5165\u300cChatGPT AI\u95ee\u7b54\u52a9\u624b\u300d\uff0c\u975e\u5e38\u9ad8\u5174\u80fd\u4e0e\u5927\u5bb6\u5728\u8fd9\u91cc\u76f8\u9047\u3002\n\n\u5efa\u8bae\u5927\u5bb6\u4f7f\u7528\u300c\u77e5\u8bc6\u661f\u7403 App\u300d\u8fdb\u884c\u4ea4\u6d41\uff0c\u8fd9\u80fd\u53ca\u65f6\u6536\u5230\u6211\u7684\u6d88\u606f\uff0c\u66f4\u597d\u5730\u4e0e\u6211\u4e92\u52a8\uff0c\u83b7\u5f97\u66f4\u591a\u6210\u957f\u4e0e\u542f\u53d1\u3002\n\n\u70b9\u51fb\u4e0b\u65b9\u94fe\u63a5\u8fdb\u884c\u4e0b\u8f7d\u5b89\u88c5\uff0c\u671f\u5f85\u5728 App \u91cc\u4e0e\u5927\u5bb6\u66f4\u6df1\u5165\u5730\u4ea4\u6d41\u3002\n<e type=\"web\" href=\"https%3A%2F%2Fzsxq.com%2Fapp.html\" title=\"https%3A%2F%2Fzsxq.com%2Fapp.html\" \/>"},"show_comments":[{"comment_id":188114418215822,"create_time":"2023-11-07T13:01:28.640+0800","owner":{"user_id":415881842415518,"name":"\u4e0e\u53ef","avatar_url":"https:\/\/images.zsxq.com\/FuZZRlJTJ0drYd4ehGW0oEIK0OXZ?e=1704038399&token=kIxbL07-8jAj8w1n4s9zv64FuZZNEATmlU_Vm6zD:hZsDa7bttAQU_zv6fvdHghakuQ4=","location":"\u56db\u5ddd"},"text":"1","likes_count":0,"rewards_count":0,"sticky":false}],"likes_count":0,"rewards_count":0,"comments_count":1,"reading_count":1,"readers_count":1,"digested":false,"sticky":false,"create_time":"2023-11-07T11:02:53.197+0800","user_specific":{"liked":false,"subscribed":false}}]}}

Process finished with exit code 0
```

后面为了方便选择了“我的收藏”进行爬取

![image-20231107135900325](https://raw.sevencdn.com/Bo-Vane/picgo/main/img/202311071359187.png)

爬取的url变成https://api.zsxq.com/v2/users/self/favorites?count=30

爬取之后也没问题，然后就是对于评论按钮接口的爬取

```
https://api.zsxq.com/v2/topics/588541818448524/comments
```

这是接口url，然后我们需要将我们的req的cookie添加，然后我们要评论的内容即req的data要加上(payload里req_data找到copy value即可)，代码就是：

```java
@Test
public void answer() throws IOException {
    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/588541818448524/comments");
    post.addHeader("cookie",${cookie});
    post.addHeader("Content-Type","application/json; charset=UTF-8");

    //把中间的1换成我不会啊就是第二次评论的内容
    String paramJson = "{\n" +
        "  \"req_data\": {\n" +
        "    \"text\": \"1\\n\",\n" +
        "    \"image_ids\": [],\n" +
        "    \"mentioned_user_ids\": []\n" +
        "  }\n" +
        "}";

    //设置请求带的实体对象，req_data
    StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
    post.setEntity(stringEntity);

    CloseableHttpResponse response = httpClient.execute(post);

    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
        String res = EntityUtils.toString(response.getEntity());
        System.out.println(res);
    }else {
        System.out.println(response.getStatusLine().getStatusCode());
    }
}
```

运行之后成功输出，看一下评论，回答了我不会啊

![image-20231107141432062](https://raw.sevencdn.com/Bo-Vane/picgo/main/img/202311071414938.png)

## 3.知识星球接口领域服务开发

开发接口领域，idea右下角的main分支那里新拉一个分支

![image-20231107142733881](https://raw.sevencdn.com/Bo-Vane/picgo/main/img/202311071427658.png)

加完分支先push上去

建包：

![image-20231107144647231](https://raw.sevencdn.com/Bo-Vane/picgo/main/img/202311071446403.png)

### 3.1 数据转java实体

json转java实体的网站：https://www.sojson.com/json2entity.html

再运行一遍二中的测试，获取一下json数据,转化结果：

```java
==================================
package ;
public class Group
{
    private int group_id;

    private String name;

    private String type;

    private String background_url;

    public void setGroup_id(int group_id){
        this.group_id = group_id;
    }
    public int getGroup_id(){
        return this.group_id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
    public void setBackground_url(String background_url){
        this.background_url = background_url;
    }
    public String getBackground_url(){
        return this.background_url;
    }
}

==================================
package ;
public class Owner
{
    private int user_id;

    private String name;

    private String avatar_url;

    private String location;

    public void setUser_id(int user_id){
        this.user_id = user_id;
    }
    public int getUser_id(){
        return this.user_id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setAvatar_url(String avatar_url){
        this.avatar_url = avatar_url;
    }
    public String getAvatar_url(){
        return this.avatar_url;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public String getLocation(){
        return this.location;
    }
}

==================================
package ;
public class Task
{
    private Owner owner;

    private String text;

    public void setOwner(Owner owner){
        this.owner = owner;
    }
    public Owner getOwner(){
        return this.owner;
    }
    public void setText(String text){
        this.text = text;
    }
    public String getText(){
        return this.text;
    }
}

```

太多了这里只整三个，把所有实体类挨个复制粘贴到vo包下即可，注意要把id改成String类型不然会出问题

### 3.2 处理接口和实现类

然后去zsxq接口定义方法，主要定义两个东西，一个获取问题，一个回答

```java
public interface IZsxqApi {

    QuestionAggregates queryQuestionTopicId(String groupId, String cookie) throws IOException;

    boolean answer(String groupId,String cookie,String topicId,String text);
}
```

新建service包去写实现类

记得pom引入依赖，从interface层copy即可，去掉不要的。

然后就可以写实现类了，根据我们之前爬虫的那个单元测试类来改就可以(参数)：

```java
 @Override
    public QuestionAggregates queryQuestionTopicId(String groupId, String cookie) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/+"+groupId+"/topics?scope=by_owner&count=20");
        get.addHeader("cookie",cookie);
        get.addHeader("Content-Type","application/json; charset=UTF-8");

        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String jsonStr = EntityUtils.toString(response.getEntity());
            return JSON.parseObject(jsonStr,QuestionAggregates.class);
        }else {
            throw new RuntimeException("Err code:"+ response.getStatusLine().getStatusCode());
        }

    }
```

这个是获取问题.接下来是回答

```java
@Override
    public boolean answer(String groupId, String cookie, String topicId, String text) throws IOException{
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/"+topicId+"/comments");
        post.addHeader("cookie",cookie );
        post.addHeader("Content-Type","application/json; charset=UTF-8");
        //告诉他这些东西是从浏览器获取
        post.addHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36");

//        String paramJson = "{\n" +
//                "  \"req_data\": {\n" +
//                "    \"text\": \"我也不会啊\\n\",\n" +
//                "    \"image_ids\": [],\n" +
//                "    \"mentioned_user_ids\": []\n" +
//                "  }\n" +
//                "}";

        //设置请求带的实体对象，req_data
        Req_data req_data = new Req_data();
        req_data.setText(text);
        AnswerReq answerReq = new AnswerReq(req_data);
        String paramJson = JSONObject.fromObject(answerReq).toString();

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("回答星球问题的结果，groupId:{} topicId:{} jsonStr:{}",groupId,topicId,jsonStr);

            AnswerRes answerRes = JSON.parseObject(jsonStr, AnswerRes.class);
            System.out.println(answerRes);
            return answerRes.isSucceed();
        }else {
            throw new RuntimeException("Err code:"+ response.getStatusLine().getStatusCode());
        }
    }

```

写好了之后呢记得加上@Service注解。

然后去接口层测试，添加@Springboot注解进行测试

我们的cookie和groupID都是配置好的不变的，所以我们直接去yml文件配置好即可：

```yml
 groupId: yours
  cookie: yourcookie
```

**注意参数冒号后面要空一格**

```java
@SpringBootTest

public class SpringBootRunTest {

    private Logger logger = LoggerFactory.getLogger(SpringBootRunTest.class);

    @Value("${chatbot-api.groupId}")
    private String groupId;

    @Value("${chatbot-api.cookie}")
    private String cookie;

    @Resource
    private IZsxqApi zsxqApi;

    @Test
    public void test_zsxqApi() throws IOException {
//        QuestionAggregates question = zsxqApi.queryQuestion(groupId, cookie);
//        System.out.println(question.toString());
        System.out.println(groupId);
    }
}

```

**然后发现取到的groupId的值一直为null，很烦**

**这是因为类没有被springboot管理？还需要加上注解@RunWith(SpringRunner.class)**

``` 
回答星球问题的结果，groupId:88885181481252  jsonStr:{"succeeded":true,"resp_data":{"favorites":[{"favor_time":"2023-11-07T15:01:03.118+0800","topic":{"topic_id":588541818448524,"group":{"group_id":88885181481252,"name":"ChatGPT AI\u95ee\u7b54\u52a9\u624b","type":"pay","background_url":"https:\/\/images.zsxq.com\/Fkf5upPCNKZ8Gq_2BkZl-LuOKskK?e=1704038399&token=kIxbL07-8jAj8w1n4s9zv64FuZZNEATmlU_Vm6zD:Yhii7s_xOL0Le9nIvp--xNwR0as="},"type":"task","task":{"owner":{"user_id":415881842415518,"name":"\u4e0e\u53ef","avatar_url":"https:\/\/images.zsxq.com\/FuZZRlJTJ0drYd4ehGW0oEIK0OXZ?e=1704038399&token=kIxbL07-8jAj8w1n4s9zv64FuZZNEATmlU_Vm6zD:hZsDa7bttAQU_zv6fvdHghakuQ4=","location":"\u56db\u5ddd"},"text":"111"},"statistics":{"submitted_count":0},"enabled_task":true,"show_solutions":true,"show_comments":[{"comment_id":188114418458142,"create_time":"2023-11-07T14:00:47.650+0800","owner":{"user_id":415881842415518,"name":"\u4e0e\u53ef","avatar_url":"https:\/\/images.zsxq.com\/FuZZRlJTJ0drYd4ehGW0oEIK0OXZ?e=1704038399&token=kIxbL07-8jAj8w1n4s9zv64FuZZNEATmlU_Vm6zD:hZsDa7bttAQU_zv6fvdHghakuQ4=","location":"\u56db\u5ddd"},"text":"1","likes_count":0,"rewards_count":0,"sticky":false},{"comment_id":588445548428124,"create_time":"2023-11-07T14:12:12.043+0800","owner":{"user_id":415881842415518,"name":"\u4e0e\u53ef","avatar_url":"https:\/\/images.zsxq.com\/FuZZRlJTJ0drYd4ehGW0oEIK0OXZ?e=1704038399&token=kIxbL07-8jAj8w1n4s9zv64FuZZNEATmlU_Vm6zD:hZsDa7bttAQU_zv6fvdHghakuQ4=","location":"\u56db\u5ddd"},"text":"\u6211\u4e0d\u4f1a\u554a","likes_count":0,"rewards_count":0,"sticky":false},{"comment_id":588445582221514,"create_time":"2023-11-07T14:58:52.164+0800","owner":{"user_id":415881842415518,"name":"\u4e0e\u53ef","avatar_url":"https:\/\/images.zsxq.com\/FuZZRlJTJ0drYd4ehGW0oEIK0OXZ?e=1704038399&token=kIxbL07-8jAj8w1n4s9zv64FuZZNEATmlU_Vm6zD:hZsDa7bttAQU_zv6fvdHghakuQ4=","location":"\u56db\u5ddd"},"text":"\u6211\u4e5f\u4e0d\u4f1a\u554a","likes_count":0,"rewards_count":0,"sticky":false},{"comment_id":411225242484218,"create_time":"2023-11-08T09:37:48.611+0800","owner":{"user_id":415881842415518,"name":"\u4e0e\u53ef","avatar_url":"https:\/\/images.zsxq.com\/FuZZRlJTJ0drYd4ehGW0oEIK0OXZ?e=1704038399&token=kIxbL07-8jAj8w1n4s9zv64FuZZNEATmlU_Vm6zD:hZsDa7bttAQU_zv6fvdHghakuQ4=","location":"\u56db\u5ddd"},"text":"2","likes_count":0,"rewards_count":0,"sticky":false}],"likes_count":0,"rewards_count":0,"comments_count":4,"reading_count":1,"readers_count":1,"digested":false,"sticky":false,"create_time":"2023-11-07T13:43:21.057+0800","user_specific":{"liked":false,"subscribed":true,"submitted":false}}}]}}
2023-11-08 11:55:46.441  
INFO 17208 --- [           main] c.bo.chatbot.api.test.SpringBootRunTest  : 测试结果：{}
```

但是返回的测试结果为空，说明在结果转为对象时出现问题，修改了resp_data类后再进行测试。

报错：com.alibaba.fastjson.JSONException: parseInt error, field : comment_id

说明parse转化json的实体类对象的属性的类型不匹配

找到不匹配的类修改完类型后，成功输出：

```
寻找星球问题的结果，groupId:88885181481252  jsonStr:{"succeeded":true,"resp_data":...
INFO 20024 --- [           main] c.bo.chatbot.api.test.SpringBootRunTest  : 测试结果：{"resp_data":{"favorites":[{"favor_time":"2023-11-07T15:01:03.118+0800","topic":{"comments_count":4,"create_time":"2023-11-07T13:43:21.057+0800","digested":false,"enabled_task":true,"group":

```

输出太多了很多地方省略

然后我们把拿到的数据里面的东西取出来试试（取topic）

```java
@Test
public void test_zsxqApi() throws IOException {
    QuestionAggregates questionAggregates = zsxqApi.queryQuestion(groupId, cookie);
    /*第一次测试
        logger.info("测试结果：{}",JSON.toJSONString(questionAggregates));
        System.out.println(groupId);
        */       
    for (Favorites favorites : questionAggregates.getResp_data().getFavorites()) {
        //找到topic
        Topic topic = favorites.getTopic();
        //输出问题，测试
        logger.info("测试结果，topicId:{} question:{}",topic.getTopic_id(),topic.getTask().getText());

    }

}
```

成功输出：

```
 测试结果，topicId:588541818448524 question:111
```

把回答问题一起测试了，现在只是说没有gpt只能问什么回答什么：

![image-20231108170108464](https://raw.sevencdn.com/Bo-Vane/picgo/main/img/202311081701325.png)

成功回复111



## 4.对接GPT(GLM)，调用接口

去openai首页，找到API reference中的making requests：https://platform.openai.com/docs/api-reference

找到需要爬取的接口：https://api.openai.com/v1/chat/completions

![image-20231109110819863](https://raw.sevencdn.com/Bo-Vane/picgo/main/img/202311091108838.png)

写一个单元测试，调用ChatGPT接口：

```java
@Test
    public void test_chatGPT() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://api.openai.com/v1/chat/completions");
        //根据making requests中给出的头部信息进行添加，这个Authorization的value的API key需要申请创建
        post.addHeader("Authorization","Bearer sk-your_key");
        post.addHeader("Content-Type","application/json; charset=UTF-8");

        //请求实体json信息
        String paramJson = "{\n" +
                "     \"model\": \"gpt-3.5-turbo\",\n" +
                "     \"messages\": [{\"role\": \"user\", \"content\": \"帮我写一个Java冒泡排序!\"}],\n" +
                "     \"temperature\": 0.7\n" +
                "   }";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        }else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }
```

![image-20231109130600500](https://raw.sevencdn.com/Bo-Vane/picgo/main/img/202311091306828.png)

去https://platform.openai.com/api-keys申请一个key，记得保留Bearer，复制上去

```java
post.addHeader("Authorization","Bearer sk-yourkey");
```

代理服务器，运行一下得到结果：

```java
429 too many requests
```

看来得冷静一下再往后做

还是不行啊，尝试使用文心一言：

在api官网文档上找到请求参数和请求头https://luckycola.com.cn/public/docs/shares/api/openWx.html

结果官网是坨shit根本进不去等不了拿不到apikey

尝试chatglm：https://open.bigmodel.cn/dev/api#http这是文档

去https://open.bigmodel.cn/usercenter/apikeys添加一个apikey，然后流程跟ChatGPT那个就差不多

去https://open.bigmodel.cn/dev/api#chatglm_turbo可以得到请求地址，参数

需要token鉴权，要引入JWT依赖

在调用模型接口时需要传鉴权 token 进行认证；当前平台鉴权 token 由用户端生成，鉴权 token 生成采用标准 JWT 中提供的创建方法生成（详细参考：https://jwt.io/introduction）。

搞了半天终于把JWT搞对了，我用的是sse调用但是输出太多了，**后面一定要改成调用同步调用的接口！！！！**

最终调用的接口： https://open.bigmodel.cn/api/paas/v3/model-api/chatglm_turbo/invoke

上代码：

```java
  @Test
    public void test_chatGPT() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://open.bigmodel.cn/api/paas/v3/model-api/chatglm_turbo/invoke");

        //jwt鉴权
        //设置过期时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MILLISECOND,200000);

        Map headers = new HashMap();
        headers.put("alg","HS256");
        headers.put("sign_type","SIGN");

        Map payload = new HashMap();
        payload.put("api_key", "your_key");
        payload.put("exp",instance.getTime());
        payload.put("timestamp", System.currentTimeMillis());

        String token = JWT.create()
                .withHeader(headers)
                .withPayload(payload)
                .sign(Algorithm.HMAC256("your_sign"));

        //根据making requests中给出的头部信息进行添加，这个Authorization的value的API key需要申请创建
        post.addHeader("Authorization",token);
        post.addHeader("Content-Type","application/json; charset=UTF-8");

        //请求实体json信息
        String paramJson = "{\n" +
                "     \"model\": \"chatglm_turbo\",\n" +
                "     \"prompt\": [{\"role\": \"user\", \"content\": \"帮我写一个java冒泡排序\"}]\n" +
//                "     \"temperature\": 0.95,\n" +
//                "     \"tcp_p\": 0.7,\n" +
//                "     \"incremental\": true\n" +
                "   }";




        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);


        CloseableHttpResponse response = httpClient.execute(post);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        }else {
            System.out.println(response.getStatusLine().getStatusCode());
            System.out.println(response.getStatusLine().getReasonPhrase());
        }
    }
```

终于成功：

```
{"code":200,"msg":"操作成功","data":{"request_id":"8098028963974553570","task_id":"8098028963974553570","task_status":"SUCCESS","choices":[{"role":"assistant","content":"\" 当然可以！以下是使用Java编写的一个冒泡排序算法：\\n\\n```java\\npublic class BubbleSort {\\n    public static void main(String[] args) {\\n        int[] arr = {64, 34, 25, 12, 22, 11, 90};\\n        bubbleSort(arr);\\n        System.out.println(\\\"排序后的数组：\\\");\\n        for (int i = 0; i < arr.length; i++) {\\n            System.out.print(arr[i] + \\\" \\\");\\n        }\\n    }\\n\\n    public static void bubbleSort(int[] arr) {\\n        for (int i = 0; i < arr.length - 1; i++) {\\n            boolean isSorted = true;\\n            for (int j = 0; j < arr.length - 1 - i; j++) {\\n                if (arr[j] > arr[j + 1]) {\\n                    int temp = arr[j];\\n                    arr[j] = arr[j + 1];\\n                    arr[j + 1] = temp;\\n                    isSorted = false;\\n                }\\n            }\\n            if (isSorted) {\\n                break;\\n            }\\n        }\\n    }\\n}\\n```\\n\\n这个程序首先定义了一个数组`arr`，并调用`bubbleSort()`方法对其进行排序。排序后的数组将打印到控制台。\\n\\n在`bubbleSort()`方法中，我们使用两个嵌套的for循环来比较相邻的元素并交换它们的位置。我们还使用了一个布尔变量`isSorted`来检查当前遍历中是否已经排序完成。如果没有排序完成，我们将继续遍历数组。当外部循环结束时，数组将按升序排列。\""}],"usage":{"prompt_tokens":9,"completion_tokens":418,"total_tokens":427}},"success":true}

```

注意报json parse错误的时候一定去检查json的逗号之类的

### ChatGLM接口领域服务开发

在domain层新建ai包，在ai包下创建IOpenAI接口

我们先commit bovane_zsxq_domain分支上的develop，再合并merge**(记得先切回主分支再merge other into main)**

再开一个新分支进行ChatGLM接口领域服务开发

**我们要把之前返回的回答转化为实体类以提取答案，新建model包，vo包存放组件实体，aggregates包存放返回的实体封装**

然后是service层的实现类，和之前一样复制测试代码过来改就行：

```java
@Service
public class OpenAI implements IOpenAI {
    @Value("${chatGLM-api.api_key}")
    private String api_key;

    @Value("${chatGLM-api.secret}")
    private String secret;

    @Override
    public String doChatGLM(String question) throws IOException {
        Logger logger = LoggerFactory.getLogger(OpenAI.class);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://open.bigmodel.cn/api/paas/v3/model-api/chatglm_turbo/invoke");

        //jwt鉴权
        //设置过期时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MILLISECOND,200000);

        Map headers = new HashMap();
        headers.put("alg","HS256");
        headers.put("sign_type","SIGN");

        Map payload = new HashMap();
        payload.put("api_key", api_key);
        payload.put("exp",instance.getTime());
        payload.put("timestamp", System.currentTimeMillis());

        String token = JWT.create()
                .withHeader(headers)
                .withPayload(payload)
                .sign(Algorithm.HMAC256(secret));

        //根据making requests中给出的头部信息进行添加，这个Authorization的value的API key需要申请创建
        post.addHeader("Authorization",token);
        post.addHeader("Content-Type","application/json; charset=UTF-8");

        //请求实体json信息
        String paramJson = "{\n" +
                "     \"model\": \"chatglm_turbo\",\n" +
                "     \"prompt\": [{\"role\": \"user\", \"content\": \"" + question + "\"}]\n" +
//                "     \"temperature\": 0.95,\n" +
//                "     \"tcp_p\": 0.7,\n" +
//                "     \"incremental\": true\n" +
                "   }";


        AIAnswer answer = new AIAnswer();
        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("回答问题返回的整体：{}",jsonStr);
            answer = JSON.parseObject(jsonStr,AIAnswer.class);
        }else {
            System.out.println(response.getStatusLine().getStatusCode());
            System.out.println(response.getStatusLine().getReasonPhrase());
            throw new RuntimeException("Err code:" + response.getStatusLine().getStatusCode()
                    + " " + response.getStatusLine().getReasonPhrase());
        }

        String finalAnswer = null;

        for (Choices choice : answer.getData().getChoices()) {
            finalAnswer = choice.getContent();
            logger.info("chatGLM回答：{}",finalAnswer);
        }

        return finalAnswer;
    }
}
```

但是我发现无法通过@Value注入接口层配置文件中配置的依赖项,于是重新写了一个配置文件

然后去runtest测试：

```java
@Test
public void test_openAI() throws IOException{
    String question = "请帮我写一个java冒泡排序";
    logger.info("测试结果：{}",openAI.doChatGLM(question));

}
```

第一次出现依赖循环报错：java: Annotation processing is not supported for module cycles.

解决方案：https://blog.csdn.net/weixin_45030023/article/details/109698282

![image-20231110172516722](https://raw.sevencdn.com/Bo-Vane/picgo/main/img/202311101725824.png)

测试成功了

## 5.整合知识星球与ChatGLM

现在要把glm和zsxq整合起来

去application层新建一个job包，这个包的类主要就是轮询我们的接口，首先是把两个服务引入进来，然后关联他们

```java
@EnableScheduling
@Configuration
public class ChatbotSchedule {
    private Logger logger = LoggerFactory.getLogger(ChatbotSchedule.class);

    @Value("${chatbot-api.groupId}")
    private String groupId;

    @Value("${chatbot-api.cookie}")
    private String cookie;

    @Resource
    private IZsxqApi zsxqApi;

    @Resource
    private IOpenAI openAI;

    //一分钟轮询一次，表达式网站：cron.qqe2.com
    @Scheduled(cron = "0 0/1 * * * ?")
    public void run(){
        try{


            //首先检索问题：
            QuestionAggregates questionAggregates = zsxqApi.queryQuestion(groupId, cookie);
            String topicId = null;
            String question = null;
            Topic topic;
            for (Favorites favorites : questionAggregates.getResp_data().getFavorites()) {
                //找到topic
                topic = favorites.getTopic();
                topicId = topic.getTopic_id();
                question = topic.getTask().getText();
                //输出问题，测试
                logger.info("测试结果，topicId:{} question:{}", topicId, question);
            }
            if (question == null){
                logger.info("本次检索未查询到待回答问题");
                return;
            }

            //回答问题
            String finalAnswer  = openAI.doChatGLM(question);

            //问题回复到知识星球
            boolean status = zsxqApi.answer(groupId, cookie, topicId, finalAnswer);
            logger.info("编号:{} 问题:{} 状态:{} 回复:{}", topicId, question, status, finalAnswer);

        }catch (Exception e){
            logger.info("自动回答问题异常",e);

        }finally {

        }
    }
}

```

但是非常规律地去调用接口呢，可能会被风控，所以我们要打破规律轮询，在最前面添加：

```java
//防止规律轮询
try{
    if (new Random().nextBoolean()){
        logger.info("随机打烊中...");
        return;
    }
    GregorianCalendar gregorianCalendar = new GregorianCalendar();
    int hour = gregorianCalendar.get(Calendar.HOUR_OF_DAY);
    if (hour > 22 || hour < 7){
        logger.info("打烊时间不工作");
        return;
    }
```

然后去启动类启动测试，看一分钟后是否有回复

![image-20231113150653621](https://raw.sevencdn.com/Bo-Vane/picgo/main/img/202311131507316.png)

空指针了，debug之后发现呢，是我放在知识星球问题的种类有问题，应该发布作业而不是talk

![image-20231113151055074](https://raw.sevencdn.com/Bo-Vane/picgo/main/img/202311131510978.png)

成功了。

![image-20231113151146365](https://raw.sevencdn.com/Bo-Vane/picgo/main/img/202311131511417.png)

由于我们的接口是每分钟轮询所以回答完一个问题你可以把最爱删除，重新提出一个作业达到会话的效果



## 6.打包镜像文件，部署服务到Docker

现在把我们的项目部署到容器中运行

在项目根目录下添加DockerFile：

```bash
[root@bovane idea]# ls
chatbot-api.jar  Dockerfile
[root@bovane idea]# vim Dockerfile 
[root@bovane idea]# ls
chatbot-api.jar  Dockerfile
[root@bovane idea]# cat Dockerfile 
FROM java:8

# 作者
MAINTAINER bovane777
# 配置
ENV PARAMS=""
# 时区
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
# 添加应用
ADD Chatbot-api.jar /app/

EXPOSE 8090
# 执行镜像；docker run -e PARAMS=" --chatbot-api.groupId=你的星球ID --chatbot-api.openAiKey=自行申请 --chatbot-api.cookie=登录cookie信息" -p 8090:8090 --name chatbot-api -d chatbot-api:1.0
ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS Chatbot-api.jar $PARAMS"]

```

然后在服务器build镜像：**（记得命令后一定要加一个点！）**

```bash
docker build -t chatbot-glm .
[+] Building 45.9s (8/8) FINISHED              
```

![image-20231116141527871](https://raw.sevencdn.com/Bo-Vane/picgo/main/img/202311161430709.png)

```bash
[root@bovane idea]# docker run -d -P --name bovane-chatbot chatbot-glm
1cad6bf88cb1f707482f3060d4bc3fda92709498a8b96bc6c1ad2c3a67c0e108
[root@bovane idea]# docker ps
CONTAINER ID   IMAGE         COMMAND                  CREATED              STATUS              PORTS                                         NAMES
1cad6bf88cb1   chatbot-glm   "sh -c 'java -jar $J…"   About a minute ago   Up About a minute   0.0.0.0:32773->8090/tcp, :::32773->8090/tcp   bovane-chatbot

```

打开发现已有回复说明运行成功！



## 7.多组任务服务配置

![image-20231116143300626](https://raw.sevencdn.com/Bo-Vane/picgo/main/img/202311161433594.png)

如果有另一个知识星球你也要启动这个job服务，那你又要在服务器运行一个docker，占内存！

这样完成：

![image-20231116143424071](https://raw.sevencdn.com/Bo-Vane/picgo/main/img/202311161434950.png)

新建一个包，来自动配置任务
