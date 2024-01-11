<script setup>
import { ref } from 'vue';
import { Edit, Delete } from '@element-plus/icons-vue';
import { ElMessage, ElMessageBox } from "element-plus";
import {
    articleCategoryListService,
    articleCategoryAddService,
    articleCategoryUpdateService,
    articleCategoryDeleteService
} from '@/api/article.js';

const categories = ref([]);

// declare function
// get category list
const articleCategoryList = async () => {
    let result = await articleCategoryListService();
    categories.value = result.data;
}
articleCategoryList();

// Control variable controlling when adding category popups
const dialogVisible = ref(false);

// Add categorical data model
const categoryModel = ref({
    categoryName: '',
    categoryAlias: '',
    id: ''
});
// Add category form validation
const rules = {
    categoryName: [
        {
            required: true,
            message: 'Please enter a category name.',
            trigger: 'blur'
        },
        {
            pattern: /^.{3,32}$/,
            message: 'Category name must be non-empty and 3-32 chars.',
            trigger: 'blur'
        }
    ],
    categoryAlias: [
        {
            required: true,
            message: 'Please enter a category alias.',
            trigger: 'blur'
        },
        {
            pattern: /^.{1,32}$/,
            message: 'Category alias must be non-empty and 1-32 chars.',
            trigger: 'blur'
        }
    ]
};

// Use API to add category
const addCategory = async ()=> {
    // invoke API
    let result = await articleCategoryAddService(categoryModel.value);
    ElMessage.success(result.msg || 'Add category successful!');

    // Refresh category list after adding the new one
    await articleCategoryList();
    dialogVisible.value = false;
};
// control title of add-category pop-up
const title = ref('');
// edit exhibition of edit-category pop-up
const showEditCategoryDialogue = (row) => {
    dialogVisible.value=true;
    title.value='Edit Category';
    // copy data to show it up
    categoryModel.value.categoryName = row.categoryName;
    categoryModel.value.categoryAlias = row.categoryAlias;
    // extend an ID, sending to back-end stage
    categoryModel.value.id = row.id;
};
// update category in back-end as we edit it
const updateCategory = async () => {
    // invoke API
    let result = await articleCategoryUpdateService(categoryModel.value);
    ElMessage.success(result.msg || 'Update category successful!');

    // Refresh category list after adding the new one
    await articleCategoryList();
    dialogVisible.value = false;
};
// delete category
const deleteCategory = (row) => {
    // notify user to confirm
    // a notification pop-up is shown
    ElMessageBox.confirm(
        'Are you sure to delete this category?',
        'Please reminder',
            {
                confirmationButtonText: 'Yes',
                cancelButtonText: 'No',
                type: 'warning'
            }
    ).then(async ()=>{
        // invoke API
        let result = await articleCategoryDeleteService(row.id);
        ElMessage.success('Delete category successful');

        // Refresh category list after adding the new one
        await articleCategoryList();
    }).catch(()=>{
        ElMessage.info('Delete category cancelled');
    });
}
</script>

<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span><strong>Article Category</strong></span>
                <div class="extra">
                    <el-button type="primary"
                               @click="
                               dialogVisible=true;
                               title='Add Category';
                               categoryModel.categoryName = '';
                               categoryModel.categoryAlias = '';
                            ">
                        Add Category
                    </el-button>
                </div>
            </div>
        </template>
        <el-table :data="categories" style="width: 100%">
            <el-table-column label="No."
                             width="100"
                             type="index"
            />
            <el-table-column label="Category Name"
                             prop="categoryName"
            />
            <el-table-column label="Category Alias"
                             prop="categoryAlias"
            />
            <el-table-column label="Action"
                             width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit"
                               circle plain
                               type="primary"
                               @click="showEditCategoryDialogue(row)"
                    />
                    <el-button :icon="Delete"
                               circle plain
                               type="danger"
                               @click="deleteCategory(row)"
                    />
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="No data" />
            </template>
        </el-table>

        <!-- Add-Category Popups -->
        <el-dialog v-model="dialogVisible"
                   :title="title"
                   width="30%">
            <el-form :model="categoryModel"
                     :rules="rules"
                     label-width="100px"
                     style="padding-right: 30px">
                <el-form-item label="Name"
                              prop="categoryName">
                    <el-input v-model="categoryModel.categoryName"
                              minlength="1"
                              maxlength="10"
                    />
                </el-form-item>
                <el-form-item label="Alias"
                              prop="categoryAlias">
                    <el-input v-model="categoryModel.categoryAlias"
                              minlength="1"
                              maxlength="15"
                    />
                </el-form-item>
            </el-form>
            <template #footer>
        <span class="dialog-footer">
            <el-button @click="dialogVisible=false">
                Cancel
            </el-button>
            <el-button type="primary"
                       @click="title==='Add Category' ?
                               addCategory() : updateCategory() "

            >
                Confirm
            </el-button>
        </span>
            </template>
        </el-dialog>
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
    }
}
</style>