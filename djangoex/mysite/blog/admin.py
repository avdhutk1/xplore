from django.contrib import admin
from .models import Post

# register Post in admin site - here it will default to the django provided model and admin site
# uncomment to see the default admin site
#admin.site.register(Post)

# to customize the admin site use below commands
# this tells the django admin site that the model is registered using a custom class that extends ModelAdmin
# It has a decorator also
@admin.register(Post)
class PostAdmin(admin.ModelAdmin):
    list_display = ('title', 'slug', 'author', 'publish', 'status')
# Register your models here.
