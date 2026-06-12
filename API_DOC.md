# 鑫东后台管理系统 - 后端接口文档

## 一、基础信息

- **服务地址**: `http://localhost:8080`
- **API 前缀**: `/api`
- **认证方式**: JWT Token（POST 请求需在 Header 中携带 `Authorization: Bearer <token>`）
- **跨域支持**: 已配置 CORS，前端可直接调用

---

## 二、分类管理接口（Category）

### 2.1 接口列表

| 方法 | 路径 | 描述 | 权限 |
|------|------|------|------|
| GET | `/api/categories` | 获取所有分类列表 | 公开 |
| GET | `/api/categories/tree` | 获取分类树形结构 | 公开 |
| GET | `/api/categories/frontend/tree` | 前端获取分类树（可按品牌筛选） | 公开 |
| GET | `/api/categories/frontend/list` | 前端获取分类列表 | 公开 |
| GET | `/api/categories/{id}` | 获取单个分类详情 | 公开 |
| GET | `/api/categories/parent/{parentId}` | 获取子分类列表 | 公开 |
| GET | `/api/categories/brand/{brandId}` | 获取品牌下的分类 | 公开 |
| GET | `/api/categories/level/{level}` | 获取指定层级分类 | 公开 |
| POST | `/api/categories` | 创建分类 | ADMIN |
| PUT | `/api/categories/{id}` | 更新分类 | ADMIN |
| DELETE | `/api/categories/{id}` | 删除分类 | ADMIN |

### 2.2 请求参数

**POST/PUT 请求体**:
```json
{
  "name": "分类名称",
  "parentId": 0,
  "brandId": 1,
  "level": 1,
  "sort": 0,
  "status": 1
}
```

**GET `/api/categories/frontend/tree` 参数**:
| 参数 | 类型 | 必填 | 描述 |
|------|------|------|------|
| brandId | Long | 否 | 品牌ID，筛选指定品牌下的分类 |

### 2.3 响应示例

```json
{
  "id": 1,
  "name": "一级分类",
  "parentId": 0,
  "brandId": 1,
  "brandName": "品牌名称",
  "level": 1,
  "sort": 0,
  "status": 1,
  "createdAt": "2026-06-12T10:00:00",
  "updatedAt": "2026-06-12T10:00:00",
  "children": []
}
```

---

## 三、产品系列接口（ProductSeries）

### 3.1 接口列表

| 方法 | 路径 | 描述 | 权限 |
|------|------|------|------|
| GET | `/api/product-series` | 获取所有系列 | 公开 |
| GET | `/api/product-series/brand/{brandId}` | 获取品牌下的系列 | 公开 |
| GET | `/api/product-series/frontend/list` | 前端获取系列列表 | 公开 |
| GET | `/api/product-series/{id}` | 获取单个系列详情 | 公开 |
| POST | `/api/product-series` | 创建系列 | ADMIN |
| PUT | `/api/product-series/{id}` | 更新系列 | ADMIN |
| DELETE | `/api/product-series/{id}` | 删除系列 | ADMIN |

### 3.2 请求参数

**POST/PUT 请求体**:
```json
{
  "name": "系列名称",
  "description": "系列描述",
  "brandId": 1,
  "coverImageUrl": "https://example.com/image.jpg",
  "enabled": true,
  "sortOrder": 0
}
```

**GET `/api/product-series/frontend/list` 参数**:
| 参数 | 类型 | 必填 | 描述 |
|------|------|------|------|
| brandId | Long | 否 | 品牌ID，筛选指定品牌下的系列 |

### 3.3 响应示例

```json
{
  "id": 1,
  "name": "系列名称",
  "description": "系列描述",
  "brandId": 1,
  "brandName": "品牌名称",
  "coverImageUrl": "https://example.com/image.jpg",
  "enabled": true,
  "sortOrder": 0,
  "createdAt": "2026-06-12T10:00:00",
  "updatedAt": "2026-06-12T10:00:00"
}
```

---

## 四、产品管理接口（Product）

### 4.1 接口列表

| 方法 | 路径 | 描述 | 权限 |
|------|------|------|------|
| GET | `/api/products` | 获取所有产品 | 公开 |
| GET | `/api/products/{id}` | 获取产品详情 | 公开 |
| GET | `/api/products/frontend/search` | 前端搜索产品 | 公开 |
| GET | `/api/products/frontend/detail` | 前端获取产品详情 | 公开 |
| POST | `/api/products` | 创建产品 | ADMIN/EDITOR |
| PUT | `/api/products/{id}` | 更新产品 | ADMIN/EDITOR |
| DELETE | `/api/products/{id}` | 删除产品 | ADMIN/EDITOR |

### 4.2 请求参数

**POST/PUT 请求体**:
```json
{
  "name": "产品名称",
  "model": "产品型号",
  "summary": "产品简介",
  "detailDescription": "产品详情",
  "price": 999.00,
  "coverImageUrl": "https://example.com/cover.jpg",
  "brandId": 1,
  "sceneId": 2,
  "typeId": 3,
  "categoryId": 4,
  "seriesId": 5,
  "detailImages": ["https://example.com/img1.jpg", "https://example.com/img2.jpg"],
  "parameters": {"颜色": "红色", "尺寸": "XL"},
  "enabled": true,
  "sortOrder": 0
}
```

**GET `/api/products/frontend/search` 参数**:
| 参数 | 类型 | 必填 | 描述 |
|------|------|------|------|
| brandId | Long | 否 | 品牌ID |
| typeId | Long | 否 | 产品类型ID |
| sceneId | Long | 否 | 产品场景ID |
| parameterKey | String | 否 | 参数键名 |
| parameterValue | String | 否 | 参数值（模糊匹配） |

**GET `/api/products/frontend/detail` 参数**:
| 参数 | 类型 | 必填 | 描述 |
|------|------|------|------|
| id | Long | 是 | 产品ID |
| enabled | Boolean | 否 | 是否启用（默认true） |

### 4.3 响应示例

```json
{
  "id": 1,
  "name": "产品名称",
  "model": "型号",
  "summary": "简介",
  "detailDescription": "详情",
  "price": 999.00,
  "coverImageUrl": "https://example.com/cover.jpg",
  "brandId": 1,
  "sceneId": 2,
  "typeId": 3,
  "categoryId": 4,
  "seriesId": 5,
  "brandName": "品牌名称",
  "sceneName": "场景名称",
  "typeName": "类型名称",
  "categoryName": "分类名称",
  "seriesName": "系列名称",
  "detailImages": ["https://example.com/img1.jpg"],
  "parameters": {"颜色": "红色"},
  "enabled": true,
  "sortOrder": 0,
  "createdAt": "2026-06-12T10:00:00",
  "updatedAt": "2026-06-12T10:00:00"
}
```

---

## 五、入驻品牌接口（MallBrand）

### 5.1 接口列表

| 方法 | 路径 | 描述 | 权限 |
|------|------|------|------|
| GET | `/api/mall-brands` | 获取所有品牌 | 公开 |
| GET | `/api/mall-brands/{id}` | 获取品牌详情 | 公开 |
| GET | `/api/mall-brands/frontend/detail` | 前端获取品牌详情（含产品类型） | 公开 |
| POST | `/api/mall-brands` | 创建品牌 | ADMIN |
| PUT | `/api/mall-brands/{id}` | 更新品牌 | ADMIN |
| DELETE | `/api/mall-brands/{id}` | 删除品牌 | ADMIN |

### 5.2 请求参数

**POST/PUT 请求体**:
```json
{
  "name": "品牌名称",
  "logoUrl": "https://example.com/logo.png",
  "slogan": "品牌口号",
  "introduction": "品牌介绍",
  "promoImageUrl": "宣传图片URL",
  "promoVideoUrl": "宣传视频URL",
  "mobilePromoVideoUrl": "移动端宣传视频URL",
  "detailDescription": "详细描述",
  "enabled": true,
  "sortOrder": 0
}
```

**GET `/api/mall-brands/frontend/detail` 参数**:
| 参数 | 类型 | 必填 | 描述 |
|------|------|------|------|
| brandId | Long | 否 | 品牌ID，默认返回第一个启用的品牌 |

### 5.3 响应示例

```json
{
  "brands": [...],
  "currentBrand": {
    "id": 1,
    "name": "品牌名称",
    "logoUrl": "https://example.com/logo.png",
    "slogan": "口号",
    "introduction": "介绍",
    "promoImageUrl": "图片URL",
    "promoVideoUrl": "视频URL",
    "mobilePromoVideoUrl": "移动端视频URL",
    "detailDescription": "详情",
    "enabled": true,
    "sortOrder": 0,
    "createdAt": "2026-06-12T10:00:00",
    "updatedAt": "2026-06-12T10:00:00"
  },
  "productTypes": [...]
}
```

---

## 六、产品类型接口（ProductType）

### 6.1 接口列表

| 方法 | 路径 | 描述 | 权限 |
|------|------|------|------|
| GET | `/api/product-types` | 获取所有类型 | 公开 |
| GET | `/api/product-types/brand/{brandId}` | 获取品牌下的类型 | 公开 |
| GET | `/api/product-types/frontend/list` | 前端获取类型列表 | 公开 |
| GET | `/api/product-types/{id}` | 获取类型详情 | 公开 |
| POST | `/api/product-types` | 创建类型 | ADMIN |
| PUT | `/api/product-types/{id}` | 更新类型 | ADMIN |
| DELETE | `/api/product-types/{id}` | 删除类型 | ADMIN |

### 6.2 请求参数

**POST/PUT 请求体**:
```json
{
  "name": "类型名称",
  "imageUrl": "https://example.com/image.jpg",
  "description": "类型描述",
  "brandId": 1,
  "enabled": true,
  "sortOrder": 0
}
```

**GET `/api/product-types/frontend/list` 参数**:
| 参数 | 类型 | 必填 | 描述 |
|------|------|------|------|
| brandId | Long | 否 | 品牌ID，默认返回第一个启用品牌的类型 |

---

## 七、团队成员接口（TeamMember）

### 7.1 接口列表

| 方法 | 路径 | 描述 | 权限 |
|------|------|------|------|
| GET | `/api/team-members` | 获取团队成员列表 | 公开 |
| GET | `/api/team-members/{id}` | 获取成员详情 | 公开 |
| POST | `/api/team-members` | 创建成员 | ADMIN |
| PUT | `/api/team-members/{id}` | 更新成员 | ADMIN |
| DELETE | `/api/team-members/{id}` | 删除成员 | ADMIN |

### 7.2 请求参数

**POST/PUT 请求体**:
```json
{
  "name": "成员姓名",
  "nickname": "昵称",
  "title": "职称",
  "position": "职位",
  "photoUrl": "头像URL",
  "email": "邮箱",
  "phone": "电话",
  "department": "部门",
  "description": "个人介绍",
  "enabled": true,
  "sortOrder": 0
}
```

---

## 八、公共响应格式

### 8.1 成功响应

```json
{
  "id": 1,
  "name": "数据名称",
  ...
}
```

### 8.2 错误响应

```json
{
  "timestamp": "2026-06-12T10:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "xxx not found",
  "path": "/api/xxx"
}
```

---

## 九、权限说明

| 角色 | 权限范围 |
|------|----------|
| 匿名用户 | 所有 GET 接口 |
| ADMIN | 所有接口（增删改查） |
| EDITOR | 产品的增删改 |

---

## 十、其他公开接口

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/api/health` | 健康检查 |
| POST | `/api/auth/login` | 用户登录 |
| GET | `/api/banners` | 获取轮播图 |
| GET | `/api/cases` | 获取案例 |
| GET | `/api/customer-reviews` | 获取客户评价 |
| GET | `/api/brands` | 获取品牌 |
| GET | `/api/product-scenes` | 获取产品场景 |

---

*文档生成时间: 2026-06-12*