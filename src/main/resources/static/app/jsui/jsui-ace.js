(function(angular, ace) {
    'use strict';
    
    var jsui = angular.module("JsUI");
    
    /**
     * Inits ACE on the given element
     * @param editorEl the element to use
     * @returns the ACE editor
     */
    function initAce(editorEl) {
        var editor = ace.edit(editorEl[0]);
        editor.setTheme("ace/theme/tomorrow");
        editor.session.setMode("ace/mode/sql");
        editor.setShowPrintMargin(false);
        editor.setHighlightActiveLine(true);
        editor.setHighlightGutterLine(true);
        
        editor.setOptions({
            enableBasicAutocompletion: true
        });
        
        editor.$blockScrolling = Infinity; // TODO suppress message
        return editor;
    }

    jsui.component('jsuiAce', {
        /*
        transclude: {
            'leftCommand': '?jsuiLeftCommand',
            'rightCommand': '?jsuiRightCommand'
        },
        */
        bindings: {
            text: '=',
            selected: '=?',
            currentLine: '=?',
            onEnter: '&?',
            onAutocomplete: '&?'
        },
        controller: ['$element', '$timeout', function ($element, $timeout) {
            var that = this,
                editorEl  = $element.find('.jsui-ace'),
                langTools = ace.require("ace/ext/language_tools"),
                editor = initAce(editorEl),
                skipSetEditorText = false,
                setEditorText = function() {
                    if (!editor || skipSetEditorText) return;
                    if (that.text !== editor.getValue()) {
                        editor.setValue(that.text);
                        editor.navigateFileEnd();
                    }
                };
            /**
             * Constructor method
             */
            this.$onInit = function(e) {
                // first attach the curser listener, so we receive changes as soon we set a text ourself
                editor.getSession().selection.on('changeCursor', function(e) {
                    var newVal = editor.session.getLine(editor.getCursorPosition().row);
                    if (that.currentLine !== newVal) $timeout(function() { that.currentLine = newVal; });
                });
                // add the text into the editor
                setEditorText();
                // ready to get selects by the user
                editor.getSession().selection.on('changeSelection', function(e) {
                    var newVal = editor.session.getTextRange(editor.getSelectionRange());
                    if (that.selected !== newVal) $timeout(function() { that.selected = newVal; });
                });
                // now we are ready to receive inputs
                editor.getSession().on('change', function(e) {
                    if (editor.getValue() != that.text) {
                        skipSetEditorText = true;
                        $timeout(function() {
                            that.text = editor.getValue();
                            skipSetEditorText = false;
                        });
                    }
                });
                // register additional commands as supported
                if (that.onEnter) {
                    editor.commands.addCommand({
                        name: 'sqlRun',
                        bindKey: {win: 'Ctrl-Enter', mac: 'Command-Enter'},
                        exec: function(editor) {
                            var selected = that.getSelected();
                            that.onEnter({selected: selected});
                        },
                        readOnly: false
                    });
                }
                
                if (that.onAutocomplete) {
                    var customCompleter = {
                        getCompletions: function(editor, session, pos, prefix, callback) {
                            var charBefore = "";
                            if (pos.row && pos.column > 0) {
                                charBefore = editor.session.getTextRange({
                                    start: {row: pos.row, column: pos.column -1},
                                    end: pos
                                });
                            }
                            that.onAutocomplete({event: {
                                editor: editor,
                                session: session,
                                pos: pos,
                                prefix: prefix,
                                callback: callback,
                                charBefore: charBefore
                            }});
                        }
                    };
                    langTools.addCompleter(customCompleter);
                }
                // go...
                editor.focus();
            }
            this.$doCheck = setEditorText;
            /**
             * Get the current selected code by the user, either the real selection or code in the selected line.
             */
            that.getSelected = function() {
                var selected = editor.session.getTextRange(editor.getSelectionRange());
                if (selected) selected.trim();
                if (!selected || selected === "") {
                    selected = editor.session.getLine(editor.getCursorPosition().row);
                }
                return selected;
            };
        }],
        templateUrl: 'app/jsui/ace-editor.html'
    });
})(angular, ace);