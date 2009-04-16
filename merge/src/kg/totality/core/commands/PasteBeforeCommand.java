package kg.totality.core.commands;

import kg.totality.core.EditorAdaptor;
import kg.totality.core.utils.ContentType;
import newpackage.glue.TextContent;
import newpackage.position.Position;
import newpackage.utils.StringUtils;
import newpackage.vim.register.RegisterContent;

// TODO: use more Vrapper code here
public class PasteBeforeCommand extends CountAwareCommand {

	@Override
	public void execute(EditorAdaptor editorAdaptor, int count) {
		if (!editorAdaptor.getFileService().isEditable()) // TODO: test
			return;
		if (count == NO_COUNT_GIVEN)
			count = 1;
		RegisterContent registerContent = editorAdaptor.getRegisterManager().getActiveRegister().getContent();
		String text = registerContent.getText();
		TextContent content = editorAdaptor.getModelContent();
		int offset = editorAdaptor.getPosition().getModelOffset();
		int position = offset;
		if (registerContent.getPayloadType() == ContentType.LINES)
			offset = content.getLineInformationOfOffset(offset).getBeginOffset();
		content.replace(offset, 0, StringUtils.multiply(text, count));
		// TODO: compatibility option: position vs. offset for destination
		Position destination = editorAdaptor.getCursorService().newPositionForModelOffset(position);
		editorAdaptor.setPosition(destination, true);
	}

	@Override
	public CountAwareCommand repetition() {
		return this;
	}

}
