function SimpleTableView() {
	this.$tag = $('<table>').addClass('table').addClass('table-hover');
	this.header = null;
	this.model = null;
}

SimpleTableView.prototype = new BaseView();

SimpleTableView.prototype.setRowClickFunction = function(cbFunction) {
	this.rowClickFunction = cbFunction;
};

SimpleTableView.prototype.setHeader = function(header) {
	this.header = header;
	var $row = $('<tr>');
	var $thead = $('<thead>');
	$thead.append($row);
	for(var index in this.header) {
		var item = this.header[index];
		$row.append($('<th>').append(item));
	}
	this.$tag.append($thead);
};

SimpleTableView.prototype.setModel = function(model) {
	this.model = model;
	this.repaint();
};

SimpleTableView.prototype.repaint = function()  {
	var $tbody = $('<tbody>');
	this.$tag.append($tbody);
	if(this.model != undefined && this.model.length > 0) {
		for(var i in this.model) {
			var $row = $('<tr>');
			var r = this.model[i];
			if(this.rowClickFunction) {
				$row.click(r.rowClickData, this.rowClickFunction);
			}
			for(var j in r) {
				var c = r[j];
				$row.append($('<td>').append(c));
			}
			$tbody.append($row);
		}
	} else {
		var $row = $('<tr>');
		$row.append($('<td>').attr({colspan : this.header.length}).append('There is no content'));
		$tbody.append($row);
	}
};