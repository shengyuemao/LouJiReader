package com.louji.vedio;

public class VedioListIterator{

	private VedioInfo current;
	private VedioInfo previous;
	private VedioList ourList;

	public VedioListIterator(VedioList vedioList) {
		ourList = vedioList;
		reset();
	}

	public void reset() {
		current = ourList.getFirst();
		previous = null;
	}

	public boolean atEnd() {
		return (current.next == null);
	}

	public void nextLink() {
		previous = current;
		current = current.next;
	}

	public void preLink() {
		current.next = current;
		current = previous;
	}

	public VedioInfo getCurrent() {
		return this.current;
	}

	public void insertAfter(VedioModel vedioUrl) {
		VedioInfo newLink = new VedioInfo(vedioUrl);
		if (ourList.isEmpty()) {
			ourList.setFirst(newLink);
			current = newLink;
		} else {
			newLink.next = current.next;
			current.next = newLink;
			nextLink();
		}
	}

	public void insertBefore(VedioModel vedioUrl) {
		VedioInfo newLink = new VedioInfo(vedioUrl);

		if (previous == null) {
			newLink.next = ourList.getFirst();
			ourList.setFirst(newLink);
			reset();
		} else {
			newLink.next = previous.next;
			previous.next = newLink;
			current = newLink;
		}
	}

	public VedioModel deleteCurrent() {
		VedioModel value = current.vedioUrl;
		if (previous == null) {
			ourList.setFirst(current.next);
			reset();
		} else {
			previous.next = current.next;
			if (atEnd()) {
				reset();
			} else {
				current = current.next;
			}
		}

		return value;
	}

}
