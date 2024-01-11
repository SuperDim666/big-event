<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue';

import { ref } from 'vue';

// Category data model
const categories = ref( []);
const categoriesMap = ref({}); // for fast-searching

// Category ID selected by the user during Search
const categoryId = ref( '');
// State selected by the user during Search
const state = ref( '');

// Article List data model
const articles = ref( []);

// Tab-Window data model
const pageNum = ref(1);  // Current Page
const total = ref(20);   // total number of articles
const pageSize = ref(3); // Number of articles per page

// called when the number of entries per page has changed
const onSizeChange = (size) => {
    pageSize.value = size;
    articleList();
};
// called when the current page number changes.
const onCurrentChange = (num) => {
    pageNum.value = num;
    articleList();
};

// ------------------
// APIs
import {
    articleCategoryListService,
    articleListService,
    articleAddService,
    articleUpdateService,
    articleDeleteService
} from '@/api/article.js';

// get category list
const articleCategoryList = async () => {
    let result = await articleCategoryListService();
    categories.value = result.data;
    categoriesMap.value = {};
    for (let i = 0; i < categories.value.length; i++) {
        let category = categories.value[i];
        categoriesMap.value[category.id] = category.categoryName;
    }
};
articleCategoryList();

// get article list
const articleList = async () => {
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value,
        categoryId: categoryId.value || null,
        state: state.value || null
    };
    let result = await articleListService(params);
    total.value = result.data.total;
    articles.value = result.data.items;

    // deal with data:
    // expand data model with a new attribute: categoryName
    for (let i = 0; i < articles.value.length; i++) {
        let searchMapResult = categoriesMap.value[articles.value[i].categoryId];
        if (searchMapResult) {
            articles.value[i].categoryName = searchMapResult;
        }
    }
};
articleList();

// --------------------
// Add-Article drawer page
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'
import {Plus} from '@element-plus/icons-vue'

// Controls whether the drawer is displayed or not
const visibleDrawer = ref(false)

// Adding a form-data Model
const articleModel = ref({
    id: 0,
    title: '',
    categoryId: '',
    coverImg: '',
    content:'',
    state:''
})
// Add a key to force re-render the component
const editorKey = ref(0);
const clearArticleModel = () => {
    articleModel.value = {
        title: '',
        categoryId: '',
        coverImg: '',
        content: '',
        state: ''
    };
    editorKey.value++;  // Increment the key to force re-render
};

// import token for upload-image
import { useTokenStore } from '@/stores/token.js';
const tokenStore = useTokenStore();

// call-back function for upload-image success
const uploadSuccess = (result) => {
    // result: the string of OSS url returned by back-end API
    //         give it to 'articleModel.coverImg'
    articleModel.value.coverImg = result.data;
}

// add article
import { ElMessage, ElMessageBox } from "element-plus";
const drawerTitle = ref("Add a new article"); // define drawer title

const addArticle = async () => {
    let result;
    if (drawerTitle.value === "Add a new article") {
        result = await articleAddService(articleModel.value);
        ElMessage.success(result.msg || 'Add article successful');
    } else {
        result = await articleUpdateService(articleModel.value);
        ElMessage.success(result.msg || 'Edit article successful');
    }
    // disable display of add-article drawer
    visibleDrawer.value = false;
    // refresh article list
    await articleList();
}

// edit article
const showEditArticleDialogue = async (row) => {
    drawerTitle.value = "Edit article";
    for (let i = 0; i < articles.value.length; i++) {
        if (row.id === articles.value[i].id) {
            articleModel.value.id = row.id;
            articleModel.value.title = articles.value[i].title;
            articleModel.value.categoryId = articles.value[i].categoryId;
            articleModel.value.coverImg = articles.value[i].coverImg;
            articleModel.value.content = articles.value[i].content;
            articleModel.value.state = articles.value[i].state;
            break;
        }
    }
    visibleDrawer.value = true;
};
const rules = {
    title: [
        {
            required: true,
            message: 'Please enter a title.',
            trigger: 'blur'
        },
        {
            pattern: /^.{3,100}$/,
            message: 'Article title must be non-empty and 3-100 chars.',
            trigger: 'blur'
        }
    ],
    categoryId: [
        {
            required: true,
            message: 'Please select a category.',
            trigger: 'blur'
        }
    ],
    coverImg: [
        {
            required: true,
            message: 'Please select a cover image.',
            trigger: 'blur'
        }
    ],
    content: [
        {
            required: true,
            message: 'Please enter your content.',
            trigger: 'blur'
        },
        {
            pattern: /^.{0,10000}$/,
            message: 'Article content is at most 10,000 chars',
            trigger: 'blur'
        }
    ]
};

// delete article
const deleteArticle = (row) => {
    // notify user to confirm
    // a notification pop-up is shown
    ElMessageBox.confirm(
        'Are you sure to delete this article?',
        'Please reminder',
        {
            confirmationButtonText: 'Yes',
            cancelButtonText: 'No',
            type: 'warning'
        }
    ).then(async ()=>{
        // invoke API
        let result = await articleDeleteService(row.id);
        ElMessage.success('Delete article successful');

        // Refresh category list after adding the new one
        await articleList();
    }).catch(()=>{
        ElMessage.info('Delete article cancelled');
    });
}
</script>

<template>
    <el-card class="page-container">

        <!-- Headers -->
        <template #header>
            <div class="header">
                <span><strong>Article Management</strong></span>

                <div class="extra" >
                    <el-button type="primary"
                               @click="drawerTitle='Add a new article';
                                       clearArticleModel();
                                       visibleDrawer=true;"
                    >
                        Add Article
                    </el-button>
                </div>
            </div>
        </template>

        <!-- Search Form -->
        <el-form inline>
            <el-form-item label="Article Category:"
            >
                <el-select placeholder="Please choose"
                           v-model="categoryId"
                >
                    <el-option v-for="category in categories"
                               :key="category.id"
                               :label="category.categoryName"
                               :value="category.id"
                    >
                    </el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="Published State：">
                <el-select placeholder="Please choose"
                           v-model="state"
                >
                    <el-option label="Published"
                               value="Published"
                    />
                    <el-option label="Draft"
                               value="Draft"
                    />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary"
                           @click="articleList"
                >
                    Search
                </el-button>
                <el-button @click="categoryId=''; state=''; articleList();">
                    Reset
                </el-button>
            </el-form-item>
        </el-form>

        <!-- Article List -->
        <el-table :data="articles"
                  style="width: 100%"
        >
            <el-table-column label="Title"
                             width="400"
                             prop="title"
            />
            <el-table-column label="Category"
                             prop="categoryName"
            />
            <el-table-column label="Updated Date"
                             prop="updateTime"
            />
            <el-table-column label="State"
                             prop="state"
            />
            <el-table-column label="Action"
                             width="100"
            >
                <template #default="{ row }">
                    <el-button :icon="Edit"
                               circle
                               plain
                               type="primary"
                               @click="showEditArticleDialogue(row)"
                    />
                    <el-button :icon="Delete"
                               circle
                               plain
                               type="danger"
                               @click="deleteArticle(row)"
                    />
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="No Data"/>
            </template>
        </el-table>

        <!-- Tab Window -->
        <el-pagination v-model:current-page="pageNum"
                       v-model:page-size="pageSize"
                       :page-sizes="[3, 5 ,10, 15]"
                       layout="jumper, total, sizes, prev, pager, next"
                       background
                       :total="total"
                       @size-change="onSizeChange"
                       @current-change="onCurrentChange"
                       style="margin-top: 20px;
                              justify-content: flex-end"
        />

        <!-- Add-article drawer -->
        <el-drawer v-model="visibleDrawer"
                   :title="drawerTitle"
                   direction="rtl"
                   size="50%">
            <!-- Add Article Form -->
            <el-form :model="articleModel"
                     :rules="rules"
                     label-width="100px" >
                <el-form-item label="Title" >
                    <el-input v-model="articleModel.title"
                              placeholder="Please enter the title"/>
                </el-form-item>
                <el-form-item label="Category">
                    <el-select placeholder="Please choose"
                               v-model="articleModel.categoryId">
                        <el-option v-for="category in categories"
                                   :key="category.id"
                                   :label="category.categoryName"
                                   :value="category.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="Article Cover">
                    <!--
                        auto-upload: set if enable auto-upload
                        action:
                            -- set server API path
                            -- note: The url-upload automatically sends the ajax request
                                     while the ajax request also has CROSS-DOMAIN issues.
                                     So a proxy solution '/api' is needed: '/api/upload' not '/upload'
                        name: set the name of the uploaded file field
                        headers: set the request header (JWT login token) for image uploads
                        on-success: set the callback func for a successful upload
                    -->
                    <el-upload class="avatar-uploader"
                               :auto-upload="true"
                               :show-file-list="false"
                               action="/api/upload"
                               name="file"
                               :headers="{
                                   'Authorization': tokenStore.token
                               }"
                               :on-success="uploadSuccess"
                    >
                        <img v-if="articleModel.coverImg"
                             :src="articleModel.coverImg"
                             class="avatar"
                             alt="Article Cover Image"
                        />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>
                <el-form-item label="Article Content">
                    <div class="editor">
                        <!-- Use Quill to show Rich-Text-Editor -->
                        <quill-editor
                            :key="editorKey"
                            theme="snow"
                            v-model:content="articleModel.content"
                            contentType="html"
                        />
                    </div>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary"
                               @click="articleModel.state='Published';
                                       addArticle();"
                    >
                        Publish
                    </el-button>
                    <el-button type="info"
                               @click="articleModel.state='Draft';
                                       addArticle();">
                        Save Draft
                    </el-button>
                </el-form-item>
            </el-form>
        </el-drawer>
    </el-card>
</template>

<style lang="scss" scoped>
.page-container {
    min-height: 100%;
    box-sizing: border-box;
    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    };
}

/* Add-Article Drawer Style */
.avatar-uploader {
    :deep() {
        .avatar {
            width: 178px;
            height: 178px;
            display: block;
        }

        .el-upload {
            border: 1px dashed var(--el-border-color);
            border-radius: 6px;
            cursor: pointer;
            position: relative;
            overflow: hidden;
            transition: var(--el-transition-duration-fast);
        }

        .el-upload:hover {
            border-color: var(--el-color-primary);
        }

        .el-icon.avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            width: 178px;
            height: 178px;
            text-align: center;
        }
    }
}
.editor {
    width: 100%;
    :deep(.ql-editor) {
        min-height: 200px;
    }
}
</style>