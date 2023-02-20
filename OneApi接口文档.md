# OneAPI接口文档  

OneApi目前实现了“一言”、“号码归属地” 两个可用接口。

## 一言

### 接口

| 名称   | 值                                      |
|------|:---------------------------------------|
| 请求方式 | GET                                    |
| 请求地址 | `http://api.limexc.cn/api/v1/hitokoto` |

### 入参

| 参数         | 类型     | 必选  | 值                       | 备注                 |
|------------|--------|-----|-------------------------|--------------------|
| c          | String | 否   | a,b,c,d,e,f,g,h,i,j,k,l | 可多选，使用逗号隔开         |
| encode     | String | 否   | text/json               | 分别返回纯内容文本信息或Json数据 |
| min_length | int    | 否   |                         | 限制正文内容最小长度         |
| max_length | int    | 否   |                         | 限制正文内容最大长度         |

### 出参

> 当`encode`为`text`时，出参为一言正文纯文本内容。
>
> 当`encode`为`json`时，主要出参见下表。

| 参数       | 类型     | 值                       | 备注   |
|----------|--------|-------------------------|------|
| from     | String |                         | 来自   |
| hitokoto | String |                         | 一言正文 |
| type     | String | a,b,c,d,e,f,g,h,i,j,k,l | 类型   |
| uuid     | int    |                         | 唯一Id |

### 样例

#### 返回格式1

> 请求地址:`http://api.limexc.cn/api/v1/hitokoto?c=a,b&encode=text`
>
> 返回数据： 与众不同的生活方式很累人呢，因为找不到借口。

#### 返回格式2

> 请求地址:`http://api.limexc.cn/api/v1/hitokoto?c=a,b&encode=json`
>
> 返回数据：

``` json
{
    "commitFrom": "web",
    "createdAt": 1589297820,
    "creator": "酷儿",
    "creatorUid": "6",
    "from": "恋如雨止",
    "fromWho": "近藤正己",
    "hitokoto": "自己不经意的一句话，触动了别人的心。",
    "id": 1030,
    "length": 18,
    "reviewer": "4756",
    "type": "a",
    "uuid": "c5ea67d4-8ef8-4b2f-805e-7bea41b23dc3"
}
```

## 号码归属地

### 正在建设
