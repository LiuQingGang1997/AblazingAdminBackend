# 鑫东后台管理系统 - 接口变更日志

## 更新时间: 2026-06-12

---

## 一、新增接口

### 1.1 分类管理接口（Category）- 新增

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

**响应示例**:
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
  "children": []
}
```

---

### 1.2 产品系列接口（ProductSeries）- 新增

| 方法 | 路径 | 描述 | 权限 |
|------|------|------|------|
| GET | `/api/product-series` | 获取所有系列 | 公开 |
| GET | `/api/product-series/brand/{brandId}` | 获取品牌下的系列 | 公开 |
| GET | `/api/product-series/frontend/list` | 前端获取系列列表 | 公开 |
| GET | `/api/product-series/{id}` | 获取单个系列详情 | 公开 |
| POST | `/api/product-series` | 创建系列 | ADMIN |
| PUT | `/api/product-series/{id}` | 更新系列 | ADMIN |
| DELETE | `/api/product-series/{id}` | 删除系列 | ADMIN |

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

---

### 1.3 团队成员接口（TeamMember）- 新增

| 方法 | 路径 | 描述 | 权限 |
|------|------|------|------|
| GET | `/api/team-members` | 获取团队成员列表 | 公开 |
| GET | `/api/team-members/{id}` | 获取成员详情 | 公开 |
| POST | `/api/team-members` | 创建成员 | ADMIN |
| PUT | `/api/team-members/{id}` | 更新成员 | ADMIN |
| DELETE | `/api/team-members/{id}` | 删除成员 | ADMIN |

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

## 二、修改接口

### 2.1 产品管理接口（Product）- 新增字段

**新增字段**:
| 字段名 | 类型 | 描述 |
|--------|------|------|
| categoryId | Long | 所属分类ID |
| seriesId | Long | 所属系列ID |

**POST/PUT 请求体（新增部分）**:
```json
{
  "categoryId": 4,
  "seriesId": 5
}
```

**响应示例（新增字段）**:
```json
{
  "categoryId": 4,
  "seriesId": 5,
  "categoryName": "分类名称",
  "seriesName": "系列名称"
}
```

---

### 2.2 入驻品牌接口（MallBrand）- 修复问题

**问题修复**: `/api/mall-brands/frontend/detail` 接口的 `productTypes` 数据错误

**修复内容**:
- ProductTypeRepository 查询方法名修正：`MallBrandId` → `MallBrand_Id`
- 确保按品牌ID正确筛选产品类型数据

---

### 2.3 产品类型接口（ProductType）- 修复问题

**问题修复**: 查询方法名导致的数据错误

**修复内容**:
- `findAllByMallBrandIdOrderBySortOrderAsc` → `findAllByMallBrand_IdOrderBySortOrderAsc`
- `findByEnabledTrueAndMallBrandIdOrderBySortOrderAsc` → `findByEnabledTrueAndMallBrand_IdOrderBySortOrderAsc`

---

## 三、权限变更

### 3.1 新增公开接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/categories/**` | 分类相关接口 |
| GET | `/api/product-series/**` | 产品系列相关接口 |
| GET | `/api/team-members/**` | 团队成员相关接口 |

---

## 四、数据库表变更

### 4.1 新增表

| 表名 | 说明 |
|------|------|
| categories | 分类表 |
| product_series | 产品系列表 |
| team_members | 团队成员表 |

### 4.2 修改表

| 表名 | 新增字段 |
|------|----------|
| products | categoryId, seriesId |

---

## 五、关联关系说明

```
MallBrand (入驻品牌)
    │
    ├── Category (分类) - 通过 brandId 关联
    │       │
    │       └── Product (产品) - 通过 categoryId 关联
    │
    ├── ProductSeries (系列) - 通过 brandId 关联
    │       │
    │       └── Product (产品) - 通过 seriesId 关联
    │
    └── ProductType (类型) - 通过 brandId 关联
            │
            └── Product (产品) - 通过中间表关联
```

---

*文档生成时间: 2026-06-12*