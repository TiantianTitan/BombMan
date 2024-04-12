#!/bin/bash
# ~/.bashrc: executed by bash(1) for non-login shells.
# see /usr/share/doc/bash/examples/startup-files (in the package bash-doc)
# for examples

# If not running interactively, don't do anything
case "$-" in
*i*)
	;;
*)
	return
	;;
esac

# Force la coloration du prompt (true/false)
COLOR_PROMPT="true"

# don't put duplicate lines or lines starting with space in the history.
# See bash(1) for more options
HISTCONTROL="ignoreboth"

# append to the history file, don't overwrite it
shopt -s "histappend"

# for setting history length see HISTSIZE and HISTFILESIZE in bash(1)
HISTSIZE="1000"
HISTFILESIZE="2000"

# check the window size after each command and, if necessary,
# update the values of LINES and COLUMNS.
shopt -s "checkwinsize"

# If set, the pattern "**" used in a pathname expansion context will
# match all files and zero or more directories and subdirectories.
#shopt -s "globstar"

# make less more friendly for non-text input files, see lesspipe(1)
[ -x "/usr/bin/lesspipe" ] && eval "$(SHELL=/bin/sh lesspipe)"

# set variable identifying the chroot you work in (used in the prompt below)
if [ -z "${debian_chroot}" ] && [ -r "/etc/debian_chroot" ]; then
	debian_chroot="$(cat /etc/debian_chroot)"
fi

# set a fancy prompt (non-color, unless we know we "want" color)
if [ "${TERM}" == "xterm-color" ] || [ "${COLOR_PROMPT}" == "true" ]; then
	PS1='${debian_chroot:+($debian_chroot)}\[\e[01;32m\]\u@\h\[\e[00m\]:\[\e[01;34m\]\w\[\e[00m\]\$ '
else
	PS1='${debian_chroot:+($debian_chroot)}\u@\h:\w\$ '
fi

# If this is an xterm set the title to user@host:dir
case "${TERM}" in
xterm*|rxvt*)
	PS1="\[\e]0;${debian_chroot:+($debian_chroot)}\u@\h: \w\a\]$PS1"
	;;
*)
	;;
esac

# enable color support of ls and also add handy aliases
if [ -x "/usr/bin/dircolors" ]; then
	[ -r "${HOME}/.dircolors" ] && eval "$(dircolors -b ~/.dircolors)" || eval "$(dircolors -b)"
	alias ls='ls --color=auto'
	#alias dir='dir --color=auto'
	#alias vdir='vdir --color=auto'
	alias grep='grep --color=auto'
	#alias fgrep='fgrep --color=auto'
	#alias egrep='egrep --color=auto'
fi

# some more ls aliases
#alias l='ls -CF'
#alias ll='ls -l'
#alias la='ls -A'

# Alias definitions
# You may want to put all your additions into a separate file like
# ~/.bash_aliases, instead of adding them here directly.
# See /usr/share/doc/bash-doc/examples in the bash-doc package.
if [ -f "${HOME}/.bash_aliases" ]; then
	source "${HOME}/.bash_aliases"
fi

# enable programmable completion features (you don't need to enable
# this, if it's already enabled in /etc/bash.bashrc and /etc/profile
# sources /etc/bash.bashrc).
if ! shopt -oq posix; then
	if [ -f "/usr/share/bash-completion/bash_completion" ]; then
		source "/usr/share/bash-completion/bash_completion"
	elif [ -f "/etc/bash_completion" ]; then
		source "/etc/bash_completion"
	fi
fi

source /opt/local/bin/java17.env
