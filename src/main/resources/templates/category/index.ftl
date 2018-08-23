<html>
    <#include  "../common/header.ftl">

<body>
    <div id="wrapper" class="toggled">
                <#include "../common/nav.ftl">

        <div id="page-content-wrapper">
            <div class="container-fluid">
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <form role="form" method="post" action="/sell/seller/category/save">
                            <div class="form-group">
                                <label>类目名称</label>
                                <input name="categoryName" type="text" class="form-control" value="${(category.categoryName) !''}" />
                            </div>
                            <div class="form-group">
                                <label>类目类型</label>
                                <input name="categoryType" type="number" class="form-control" value="${(category.categoryType) !''}" />
                            </div>
                            <input hidden name="categoryId" type="text" value="${(category.categoryId) !''}" />
                            <button type="submit" class="btn btn-default">Submit</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>