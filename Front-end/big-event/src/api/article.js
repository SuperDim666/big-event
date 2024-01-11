import request from '@/utils/request.js';

// -----------------
// Category APIs

// invoke category-list-query API
export const articleCategoryListService = () => {
    // No need to set like this below as we set token in request interceptor
    // const tokenStore = useTokenStore();
    // return request.get('/category', {
    //     headers: {
    //         // All responsive data in Pinia doesn't need '.value'
    //         'Authorization' : tokenStore.token
    //     }
    // });
    return request.get('/category');
}

// invoke add-category API
export const articleCategoryAddService = (categoryData) => {
    return request.post('/category', categoryData);
}

// invoke update-category API
export const articleCategoryUpdateService = (categoryData) => {
    return request.put('/category', categoryData);
}

// invoke delete-category API
export const articleCategoryDeleteService = (id) => {
    return request.delete('/category?id='+id);
}

// -----------------
// List APIs

// invoke article-list-query API
export const articleListService = (params) => {
    return request.get('/article', {params: params});
}

// invoke article-add API
export const articleAddService = (articleData) => {
    return request.post('/article', articleData);
}

// invoke update-category API
export const articleUpdateService = (articleData) => {
    return request.put('/article', articleData);
}

// invoke delete-category API
export const articleDeleteService = (id) => {
    return request.delete('/article?id='+id);
}

