$(document).ready(function(){
	resize();
	
	$(window).resize(function(){
		resize();
	});
	
	$('a[href^=http://]').attr('target', '_blank');
	
	//Pretty Photo gallery
	$("a[rel^='prettyPhoto']").prettyPhoto({
		theme: 'dark_rounded',
		animationSpeed: 'fast'
	});
	
	runGallery();
	
	$('#imageBtn').click(function(){
		$('#upload').click();
	});
	
	$(function(){
		var dialog = $('.dialog');
		
		dialog.click(function(){
			var link = $(this).children('a.messageLnk')[0];
			link.click();
		});
		
		dialog.mouseenter(function(){
			var that = $(this);
			that.toggleClass("hover");
			that.children('.deleteDiv').show();
        }).mouseleave(function(){
        	var that = $(this);
        	that.toggleClass("hover");
        	that.children('.deleteDiv').hide();
        });
	});
	
});

function resize(){
	if ($('#output').width()<1100){
		if($.browser.msie && parseInt($.browser.version, 10) == 7){
			$("#myCabinetMenu").css('top', '-20px');
		}else{
			$("#myCabinetMenu").css('top', '-30px');
		}
	   
	   $("#myCabinetPage").css('margin-top', '20px');
	   $("#myCabinetPage .rightDiv").css('position', 'relative');
	}else{
		if($.browser.msie && parseInt($.browser.version, 10) == 7){
			$("#myCabinetMenu").css('top', '-40px');
		}else{
			$("#myCabinetMenu").css('top', '-50px');
		}
		$("#myCabinetPage").css('margin-top', '0px');
		$("#myCabinetPage .rightDiv").css('position', 'absolute');
	}
	
	
};

function saveMessage(url) {
	var mt = $('.editableDiv').html().replace(/=/g, '%3d');
	if(mt.length > 0){
		var wcall = wicketAjaxGet(url + '&message=' + mt, function() { }, function() { });
	}
}

function submitForm(){
	$("form.uploadForm").submit();
}

function runGallery(){
    $('.galleryPanel .galleryList:gt(0)').hide();
    setInterval(function(){
      $('.galleryPanel .galleryList:first-child').fadeOut(600)
         .next('.galleryList').fadeIn(600)
         .end().appendTo('.galleryPanel');}, 
      5000);
}

function toggleSmilePanel(){
	if($('.smilesPanel').css('display') == 'none'){ 
		 $('.smilesPanel').show('fast'); 
	} else {
		 $('.smilesPanel').css('display', 'none'); 
	}
}

function insertSmile(smile) {
	html = smile.parent().html();
    var sel, range;
    if (window.getSelection) {
        // IE9 and non-IE
        sel = window.getSelection();
        if (sel.getRangeAt && sel.rangeCount) {
            range = sel.getRangeAt(0);
            
            var element = range.collapsed || range.startContainer.childNodes.length == 0
            ? sel.focusNode
            : range.startContainer.childNodes[range.startOffset];

            var e = sel.focusNode;
            var pe = e.parentElement;
            if(e != null && (pe.getAttribute("class") == "editable" || pe.getAttribute("class") == "editableDiv")){
	            range.deleteContents();
	
	            // Range.createContextualFragment() would be useful here but is
	            // non-standard and not supported in all browsers (IE9, for one)
	            var el = document.createElement("div");
	            el.innerHTML = html;
	            var frag = document.createDocumentFragment(), node, lastNode;
	            while ( (node = el.firstChild) ) {
	                lastNode = frag.appendChild(node);
	            }
	            range.insertNode(frag);
	
	            // Preserve the selection
	            if (lastNode) {
	                range = range.cloneRange();
	                range.setStartAfter(lastNode);
	                range.collapse(true);
	                sel.removeAllRanges();
	                sel.addRange(range);
	            }
            }
        }
    } else if (document.selection && document.selection.type != "Control") {
        // IE < 9
        document.selection.createRange().pasteHTML(html);
    }
    $('.editableDiv').focus();
}
