function BaseView() {
	this.$tag = $('<div>').addClass('base_view_div');
}

BaseView.prototype.getTag = function() {
	return this.$tag;
};

BaseView.prototype.setModel = function(model) {
	this.model = model;
	this.repaint();
};

BaseView.prototype.repaint = function() {
	//implement yours...
};