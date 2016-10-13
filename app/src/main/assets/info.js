          //判断图片是否加载完成
          function IsImageLoaded()
          {
              var img=event.srcElement;

              if(img.src.indexOf("picture_place_holder.png") > 0)
              {
                  img.src = img.attributes['loadingsrc'].nodeValue;
              }
              else if(img.src.indexOf("ruanmei_webview_loadingimage.gif") > 0)
              {
              }
              else
              {
                   if(img.complete)
                   {
						//ios 浏览图片使用
                       var imagesrc = img.attributes['originsrc'].nodeValue;
                       //document.location = imagesrc.replace("http://","ruanmeipic://");
                       ProxyClickPicture.clickImg(imagesrc);
                   }
                   else
                   {
                       //未加载完成时，暂不放大图片，以后可以修改
                       //img.src = img.attributes['originsrc'].nodeValue;
                   }
              }
          }

          function imageload()
          {
              var img=event.srcElement;

              if(img.src.indexOf("ruanmei_webview_loadingimage.gif") > 0)
              {
                  img.src = img.attributes['originsrc'].nodeValue;
              }
              else
              {
              }
          }
          

