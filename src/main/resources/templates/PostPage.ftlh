<#-- @ftlvariable name="comments" type="ru.itmo.wp.domain.Post[]" -->
<#-- @ftlvariable name="post" type="ru.itmo.wp.domain.Post" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#if post??>
        <@c.post post/>
        <#if user??>
            <div class="form">
                <div class="header">Write Comment</div>
                <div class="body">
                    <form method="post" action="writeComment">
                        <div class="field">
                            <input type="hidden" name="postId" value="${post.getId()}">
                            <div class="name">
                                <label for="text">Text</label>
                            </div>
                            <div class="value">
                                <textarea id="text" name="text"><#--${post.text!}--></textarea>
                            </div>
                            <@c.error "commentForm.text"/>
                        </div>
                        <div class="button-field">
                            <input type="submit" value="Write">
                        </div>
                    </form>
                </div>
            </div>
        </#if>
        <div class="comments">
            <#list comments as comment>
                <p>Comment by ${comment.getUser().getLogin()}: ${comment.getText()}</p>
            </#list>
        </div>
    </#if>
</@c.page>