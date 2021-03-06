package net.cheney.webdav.resource.file;

import java.io.File;
import java.net.URI;

import net.cheney.motown.server.api.Path;
import net.cheney.webdav.resource.api.Lock;
import net.cheney.webdav.resource.api.LockManager;
import net.cheney.webdav.resource.api.Resource;
import net.cheney.webdav.resource.api.ResourceProvidor;
import net.cheney.webdav.resource.api.Lock.Scope;
import net.cheney.webdav.resource.api.Lock.Type;

public class FileResourceProvidor implements ResourceProvidor {

	private final File root;
	private final LockManager lockManager;

	public FileResourceProvidor(String root) {
		this(new File(root));
	}
	
	public FileResourceProvidor(File root) {
		this.root = root;
		this.lockManager = new FileResourceLockManager();
	}
	
	public final FileResource resolveResource(Path path) {
		return new FileResource(this, new File(root, path.toString()));
	}
	
	public final LockManager lockManager() {
		return lockManager;
	}
	
	private class FileResourceLockManager implements LockManager {
		
		@Override
		public boolean isLocked(Resource resource) {
			return false;
		}

		@Override
		public Lock lock(Resource resource, Type type, Scope scope) {
			return new Lock(type, scope, resource);
		}

		@Override
		public Lock unlock(Resource resource) {
			return new Lock(Type.NONE, Scope.NONE, resource);
		}
		
	}

	public URI relativizeResource(Resource resource) {
		return root.toURI().relativize(((FileResource) resource).file().toURI());
	}

}
